/*
 * Copyright (c) 2019 Nimbly Games, LLC all rights reserved
 */

import org.gradle.internal.jvm.Jvm

group = "com.nimblygames.steamsdkjni"
version = rootProject.version

plugins {
    id("com.nimblygames.gradle")
    `maven-publish`
    `cpp-library`
    xcode
    `microsoft-visual-cpp-compiler`
    `visual-studio`
}

repositories {
    jcenter()
}

val steamSdkJniHeaders = configurations.register("steamSdkJniHeaders")
dependencies {
    steamSdkJniHeaders.get()(project(":SteamSdk", "jniHeaders"))
}

val steamSdkDirPath = "${projectDir}/sdk/sdk_144"
val is64Bit = System.getProperty("os.arch").equals("amd64") || System.getProperty("os.arch").equals("x86_64")
val javaHomePathString = Jvm.current().getJavaHome().getAbsolutePath()

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

val currentPlatformNativeJar = tasks.register<Jar>("currentPlatformNativeJar")

artifacts {
    add(configurations.register(currentPlatformNativeJar.name).name, currentPlatformNativeJar)
}

publishing {
    repositories {
        maven {
            url = uri("https://artifactory.nimblygames.com/artifactory/gradle-release-local/")
            credentials {
                username = rootProject.findProperty("artifactory_user") as String
                password = rootProject.findProperty("artifactory_password") as String
            }
        }
    }
}

val currentPlatformNativePublication = publishing.publications.create<MavenPublication>(project.name) {
    groupId = project.group as String
    version = project.version as String

    artifact(currentPlatformNativeJar.get())
}

library {
    linkage.set(listOf(Linkage.SHARED))

    targetMachines.set(listOf(machines.windows.x86,
            machines.windows.x86_64,
            machines.linux.x86,
            machines.linux.x86_64,
            machines.macOS.x86,
            machines.macOS.x86_64))

    toolChains.forEach { toolChain ->
        if (toolChain is VisualCpp) {
            toolChain.setInstallDir(File("C:/Program Files (x86)/Microsoft Visual Studio/2017/Community"))
            toolChain.setWindowsSdkDir(File("C:/Program Files/Microsoft SDKs/Windows/v7.1"))
        }
    }

    binaries.configureEach {
        val binaryToolChain = toolChain

        val binaryCompileTask = compileTask.get()
        val binaryLinkTask: LinkSharedLibrary = if (this is CppSharedLibrary)
            linkTask.get()
        else
            return@configureEach

        binaryCompileTask.includes(file("$buildDir/steamSdkJniHeaders"))
        binaryCompileTask.includes(file("$javaHomePathString/include"))
        binaryCompileTask.includes(file("$steamSdkDirPath/public"))

        val osName = when {
            targetMachine.operatingSystemFamily.isWindows -> OperatingSystemFamily.WINDOWS
            targetMachine.operatingSystemFamily.isMacOs -> OperatingSystemFamily.MACOS
            targetMachine.operatingSystemFamily.isLinux -> OperatingSystemFamily.LINUX
            else -> {
                throw RuntimeException("Unknown operating system family ${targetMachine.operatingSystemFamily}")
            }
        }

        if (binaryToolChain is VisualCpp) {
            binaryCompileTask.includes(file("$javaHomePathString/include/win32"))

            binaryCompileTask.macros["DLL_EXPORT"] = null
            binaryCompileTask.macros["NG_WIN"] = ""
            binaryCompileTask.macros["_WIN32"] = ""
            binaryCompileTask.macros["WIN32"] = ""
            binaryCompileTask.macros["UNICODE"] = ""
            binaryCompileTask.macros["_UNICODE"] = ""
            binaryCompileTask.macros["_WIN32_WINNT_WINXP=0x0501"] = "" // target windows xp

            val args = mutableListOf<String>()
            binaryCompileTask.compilerArgs.get().forEach {
                args.add(it)
            }
            args.add("/O2")
            args.add("/EHs")
            args.add("/MT")
            args.add("/nologo")
            binaryCompileTask.compilerArgs.set(args)

            binaryLinkTask.linkerArgs.add("/nologo")
            if (targetMachine.architecture.getName() == "x86") {
                binaryLinkTask.linkerArgs.add("/libpath:${steamSdkDirPath}/public/steam/lib/win32")
                binaryLinkTask.linkerArgs.add("/libpath:${steamSdkDirPath}/redistributable_bin")
                binaryLinkTask.linkerArgs.add("steam_api.lib")
                binaryLinkTask.linkerArgs.add("/MACHINE:X86")
            }
            if (targetPlatform.targetMachine.architecture.getName() == "x86-64") {
                binaryLinkTask.linkerArgs.add("/libpath:${steamSdkDirPath}/public/steam/lib/win64")
                binaryLinkTask.linkerArgs.add("/libpath:${steamSdkDirPath}/redistributable_bin/win64")
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
                binaryLinkTask.linkerArgs.add("-L${steamSdkDirPath}/redistributable_bin/osx32")
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

                    binaryLinkTask.outputs.files.files
                            .stream()
                            .filter({ file -> file.name.endsWith(".dylib") })
                            .findFirst()
                            .ifPresent({ binaryFile ->
                                inputs.file(binaryFile)
                                args("-arch")
                                if (targetMachine.architecture.name == MachineArchitecture.X86) {
                                    args("i386")
                                } else if (targetMachine.architecture.name == MachineArchitecture.X86_64) {
                                    args("x86_64")
                                }
                                args("$binaryFile")
                            })
                }
            }

            currentPlatformNativeJar.configure {
                dependsOn(binaryLinkTask)

                if (targetMachine.operatingSystemFamily.isMacOs) {
                    dependsOn(macOsDylibLipo)
                }

                destinationDirectory.set(file("$buildDir/distribute"))
                archiveFileName.set("SteamSdkJni-$osName.jar")
                currentPlatformNativePublication.artifactId = archiveFileName.get()

                if (targetMachine.operatingSystemFamily.isMacOs) {
                    if (!inputs.files.contains(macOsDylibLipo.get().outputs.files.single())) {
                        from(macOsDylibLipo.get().outputs.files.single()) {
                            into("$osName")
                        }
                    }
                } else {
                    binaryLinkTask.outputs.files.forEach {
                        if (it.name.endsWith(".dll") || it.name.endsWith(".so")) {
                            from(it) {
                                into("$osName-${targetMachine.architecture.name}")
                            }
                        }
                    }
                }

                when {
                    targetMachine.operatingSystemFamily.isWindows -> {
                        val steamApiSharedLibrary = file("$steamSdkDirPath/redistributable_bin/steam_api.dll")
                        if (!inputs.files.contains(steamApiSharedLibrary)) {
                            from(steamApiSharedLibrary) {
                                into("$osName-x86")
                            }
                            from("$steamSdkDirPath/redistributable_bin/win64/steam_api64.dll") {
                                into("$osName-x86-64")
                            }
                        }
                    }
                    targetMachine.operatingSystemFamily.isMacOs -> {
                        val steamApiSharedLibrary = file("$steamSdkDirPath/redistributable_bin/osx32/libsteam_api.dylib")
                        if (!inputs.files.contains(steamApiSharedLibrary)) {
                            from(steamApiSharedLibrary) {
                                into(osName)
                            }
                        }
                    }
                    targetMachine.operatingSystemFamily.isLinux -> {
                        val steamApiSharedLibrary = file("$steamSdkDirPath/redistributable_bin/linux32/libsteam_api.so")
                        if (!inputs.files.contains(steamApiSharedLibrary)) {
                            from(steamApiSharedLibrary) {
                                into("$osName-x86")
                            }
                            from("$steamSdkDirPath/redistributable_bin/linux64/libsteam_api.so") {
                                into("$osName-x86-64")
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
}

//
val syncSteamSdkJniHeaders = tasks.register<Sync>("syncSteamSdkJniHeaders") {
    dependsOn(steamSdkJniHeaders)

    steamSdkJniHeaders.get().forEach {
        from(zipTree(it))
    }
    into("$buildDir/steamSdkJniHeaders")
}
tasks.named("assemble") {
    dependsOn(syncSteamSdkJniHeaders)
}

tasks.withType(CppCompile::class.java) {
    dependsOn(syncSteamSdkJniHeaders)
}

