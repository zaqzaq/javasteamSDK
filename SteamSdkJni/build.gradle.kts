/*
 * Copyright 2020 Nimbly Games, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("UnstableApiUsage")

import org.gradle.internal.jvm.Jvm

group = rootProject.group
version = rootProject.version

plugins {
   id("com.nimblygames.gradle")
   `cpp-library`
   xcode
   `microsoft-visual-cpp-compiler`
   `visual-studio`
}

repositories {
   jcenter()
}

/**
 * Add dependency on SteamSdk JNI headers.
 */
val steamSdkJniHeaders = configurations.register("steamSdkJniHeaders")
dependencies {
   steamSdkJniHeaders.get()(project(":SteamSdk", "jniHeaders"))
}

/**
 * Steam SDK directory.
 */
val steamSdkDirPath = "$projectDir/sdk/sdk_144"

/**
 * Java home that's running Gradle
 */
val javaHomePathString: String = Jvm.current().javaHome.absolutePath

/**
 * Create a combined library for all target platforms.
 */
val macOsDylibLipo = tasks.register<Exec>("macOsDylibLipo") {
   val outputLibName = "lib${project.name}.dylib"
   workingDir = File(buildDir, "distribute")
   val outputLibFile = File(workingDir, outputLibName)
   outputs.file(outputLibFile)
   executable = "lipo"
   args("-create")
   args("-output")
   args(outputLibName)
}

/**
 * Name for the zip artifact to publish the native libraries into.
 */
val nativeArtifactName = "SteamSdkJni-natives"

/**
 * Create a zip of all native libraries.
 */
val platformNativeZip = tasks.register<Zip>("platformNativeZip") {
   destinationDirectory.set(file("$buildDir/distribute"))
   archiveFileName.set("$nativeArtifactName.zip")
}

/**
 * Configuration for making native libraries available to other Gradle projects.
 */
val nativePlatformConfiguration = configurations.register(platformNativeZip.name)

artifacts {
   add(nativePlatformConfiguration.name, platformNativeZip)
}

library {
   linkage.set(listOf(Linkage.SHARED))

   targetMachines.set(listOf(machines.windows.x86, machines.windows.x86_64, machines.linux.x86, machines.linux.x86_64, machines.macOS.x86_64))

   binaries.configureEach {
      val binaryToolChain = toolChain

      val binaryCompileTask = compileTask.get()
      val binaryLinkTask: LinkSharedLibrary = if (this is CppSharedLibrary) linkTask.get()
      else return@configureEach

      binaryCompileTask.includes(file("$buildDir/steamSdkJniHeaders"))
      binaryCompileTask.includes(file("$javaHomePathString/include"))
      binaryCompileTask.includes(file("$steamSdkDirPath/public"))

      val osName = when {
         targetMachine.operatingSystemFamily.isWindows -> {
            OperatingSystemFamily.WINDOWS
         }
         targetMachine.operatingSystemFamily.isMacOs -> {
            OperatingSystemFamily.MACOS
         }
         targetMachine.operatingSystemFamily.isLinux -> {
            OperatingSystemFamily.LINUX
         }
         else -> {
            throw RuntimeException("Unknown operating system family ${targetMachine.operatingSystemFamily}")
         }
      }

      if (binaryToolChain is VisualCpp) {
         binaryCompileTask.includes(file("$javaHomePathString/include/win32"))

         binaryCompileTask.macros["DLL_EXPORT"] = null
         binaryCompileTask.macros["NG_WIN"] = null
         binaryCompileTask.macros["_WIN32"] = null
         binaryCompileTask.macros["WIN32"] = null
         binaryCompileTask.macros["UNICODE"] = null
         binaryCompileTask.macros["_UNICODE"] = null
         binaryCompileTask.macros["_WIN32_WINNT_WINXP=0x0501"] = null // target windows xp

         binaryCompileTask.compilerArgs.add("/O2")
         binaryCompileTask.compilerArgs.add("/EHs")
         binaryCompileTask.compilerArgs.add("/MT")
         binaryCompileTask.compilerArgs.add("/nologo")

         binaryLinkTask.linkerArgs.add("/nologo")
         if (targetMachine.architecture.name == MachineArchitecture.X86) {
            binaryLinkTask.linkerArgs.add("/libpath:$steamSdkDirPath/public/steam/lib/win32")
            binaryLinkTask.linkerArgs.add("/libpath:$steamSdkDirPath/redistributable_bin")
            binaryLinkTask.linkerArgs.add("steam_api.lib")
            binaryLinkTask.linkerArgs.add("/MACHINE:X86")
         }
         if (targetPlatform.targetMachine.architecture.name == MachineArchitecture.X86_64) {
            binaryLinkTask.linkerArgs.add("/libpath:$steamSdkDirPath/public/steam/lib/win64")
            binaryLinkTask.linkerArgs.add("/libpath:$steamSdkDirPath/redistributable_bin/win64")
            binaryLinkTask.linkerArgs.add("steam_api64.lib")
         }
         binaryLinkTask.linkerArgs.add("User32.lib")
      } else if (binaryToolChain is Gcc) {
         // compiler define linux
         if (targetPlatform.targetMachine.operatingSystemFamily.isLinux) {
            binaryCompileTask.macros["NG_LIN"] = ""
         }

         binaryCompileTask.compilerArgs.add("-fPIC")
         binaryCompileTask.compilerArgs.add("-c")
         binaryCompileTask.compilerArgs.add("-fmessage-length=0")
         binaryCompileTask.compilerArgs.add("-Wwrite-strings")

         // compiler linux
         if (targetPlatform.targetMachine.architecture.name == MachineArchitecture.X86) {
            binaryCompileTask.compilerArgs.add("-m32")
         }
         if (targetPlatform.targetMachine.architecture.name == MachineArchitecture.X86_64) {
            binaryCompileTask.compilerArgs.add("-m64")
         }

         if (targetPlatform.targetMachine.operatingSystemFamily.isLinux) {
            binaryCompileTask.includes(file("/usr/lib/jvm/java-8-openjdk-amd64/include/"))
            binaryCompileTask.includes(file("/usr/lib/jvm/java-8-openjdk-amd64/include/linux/"))
         }

         // compiler osx
         if (targetPlatform.targetMachine.operatingSystemFamily.isMacOs) {
            binaryCompileTask.compilerArgs.add("-std=c++11")
            binaryCompileTask.includes(file("/System/Library/Frameworks/JavaVM.framework/Versions/A/Headers"))
         }


         // linker linux
         if (targetPlatform.targetMachine.architecture.name == MachineArchitecture.X86) {
            println("platform is linux32")
            binaryLinkTask.lib("$steamSdkDirPath/redistributable_bin/linux32/libsteam_api.so")
         }
         if (targetMachine.architecture.name == MachineArchitecture.X86_64) {
            println("platform is linux64")
            binaryLinkTask.lib("$steamSdkDirPath/redistributable_bin/linux64/libsteam_api.so")
         }

         // linker osx
         if (targetMachine.operatingSystemFamily.isMacOs) {
            binaryLinkTask.linkerArgs.add("-L$steamSdkDirPath/redistributable_bin/osx32")
         }
      } else if (binaryToolChain is Clang) {
         binaryCompileTask.compilerArgs.add("-fPIC")
         binaryCompileTask.compilerArgs.add("-c")
         binaryCompileTask.compilerArgs.add("-fmessage-length=0")
         binaryCompileTask.compilerArgs.add("-Wwrite-strings")

         // compiler osx
         if (targetMachine.operatingSystemFamily.isMacOs) {
            binaryCompileTask.includes(file("$javaHomePathString/include"))
            binaryCompileTask.includes(file("$javaHomePathString/include/darwin"))
            binaryCompileTask.compilerArgs.add("-std=c++11")
         }

         // linker osx
         if (targetMachine.operatingSystemFamily.isMacOs) {
            binaryLinkTask.lib("$steamSdkDirPath/redistributable_bin/osx32/libsteam_api.dylib")
         }
      }

      if (binaryCompileTask.isOptimized) {
         if (targetMachine.operatingSystemFamily.isMacOs) {
            macOsDylibLipo.configure {
               dependsOn(binaryLinkTask)

               binaryLinkTask.outputs.files.files.stream().filter { file -> file.name.endsWith(".dylib") }.findFirst().ifPresent { binaryFile ->
                  inputs.file(binaryFile)
                  args("-arch")
                  if (targetMachine.architecture.name == MachineArchitecture.X86) {
                     args("i386")
                  } else if (targetMachine.architecture.name == MachineArchitecture.X86_64) {
                     args("x86_64")
                  }
                  args("$binaryFile")
               }
            }
         }

         platformNativeZip.configure {
            dependsOn(binaryLinkTask)

            if (targetMachine.operatingSystemFamily.isMacOs) {
               dependsOn(macOsDylibLipo)
            }
         }

         when {
            targetMachine.operatingSystemFamily.isWindows -> {
               val steamApiSharedLibrary = file("$steamSdkDirPath/redistributable_bin/steam_api.dll")
               platformNativeZip.configure {
                  dependsOn(binaryLinkTask)
                  if (!inputs.files.contains(steamApiSharedLibrary)) {
                     from(steamApiSharedLibrary) {
                        into("$osName-x86")
                     }
                     from("$steamSdkDirPath/redistributable_bin/win64/steam_api64.dll") {
                        into("$osName-x86-64")
                     }
                  }
                  binaryLinkTask.outputs.files.forEach {
                     if (it.name.endsWith(".dll")) {
                        from(it) {
                           into("$osName-${targetMachine.architecture.name}")
                        }
                     }
                  }
               }
            }
            targetMachine.operatingSystemFamily.isMacOs -> {
               val steamApiSharedLibrary = file("$steamSdkDirPath/redistributable_bin/osx32/libsteam_api.dylib")
               platformNativeZip.configure {
                  dependsOn(binaryLinkTask)
                  if (!inputs.files.contains(steamApiSharedLibrary)) {
                     from(steamApiSharedLibrary) {
                        into(osName)
                     }
                  }
                  if (!inputs.files.contains(macOsDylibLipo.get().outputs.files.single())) {
                     from(macOsDylibLipo.get().outputs.files.single()) {
                        into(osName)
                     }
                  }
               }
            }
            targetMachine.operatingSystemFamily.isLinux -> {
               val steamApiSharedLibrary = file("$steamSdkDirPath/redistributable_bin/linux32/libsteam_api.so")
               platformNativeZip.configure {
                  dependsOn(binaryLinkTask)
                  if (!inputs.files.contains(steamApiSharedLibrary)) {
                     from(steamApiSharedLibrary) {
                        into("$osName-x86")
                     }
                     from("$steamSdkDirPath/redistributable_bin/linux64/libsteam_api.so") {
                        into("$osName-x86-64")
                     }
                  }
                  binaryLinkTask.outputs.files.forEach {
                     if (it.name.endsWith(".so")) {
                        from(it) {
                           into("$osName-${targetMachine.architecture.name}")
                        }
                     }
                  }
               }
            }
            else -> {
               throw RuntimeException("Unknown operating system family ${targetMachine.operatingSystemFamily}")
            }
         }
      }
   }
}

/**
 * Extract the JNI header files from the SteamSdk project.
 */
val syncSteamSdkJniHeaders = tasks.register<Sync>("syncSteamSdkJniHeaders") {
   dependsOn(steamSdkJniHeaders)

   steamSdkJniHeaders.get().forEach {
      from(zipTree(it))
   }
   into("$buildDir/steamSdkJniHeaders")
}
tasks.named("assemble") {
   dependsOn(syncSteamSdkJniHeaders)
   dependsOn(platformNativeZip)
}

tasks.withType(CppCompile::class) {
   dependsOn(syncSteamSdkJniHeaders)
}

