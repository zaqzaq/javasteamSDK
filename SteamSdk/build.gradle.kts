/*
 * Copyright (c) 2019 Nimbly Games, LLC all rights reserved
 */

import com.nimblygames.gradle.junitVersion
import com.nimblygames.gradle.log4jVersion
import com.nimblygames.gradle.slf4jVersion
import org.gradle.internal.jvm.Jvm
import java.nio.ByteBuffer
import java.nio.channels.SeekableByteChannel
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption

group = "com.nimblygames.steam"
version = rootProject.version

plugins {
   id("com.nimblygames.gradle")
   `maven-publish`
   `java-library`
}

repositories {
   jcenter()
}

java {
   sourceCompatibility = JavaVersion.VERSION_1_8
   targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
   // test
   testImplementation("junit:junit:$junitVersion")

   testRuntimeOnly(project(":SteamSdkJni", "windowsPlatformNativeJar"))
   testRuntimeOnly(project(":SteamSdkJni", "linuxPlatformNativeJar"))
   testRuntimeOnly(project(":SteamSdkJni", "macOsPlatformNativeJar"))

   // logging
   implementation("org.slf4j:slf4j-api:$slf4jVersion")
   runtime("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")
   runtime("org.apache.logging.log4j:log4j-core:$log4jVersion")
}

tasks.withType(Test::class.java) {
   maxParallelForks = 1
}

// generate headers
val nativeHeaders = tasks.register("nativeHeaders") {
   dependsOn("classes")
   val outputDirectory = file("$buildDir/steamjni/cpp")
   val classesToRunJavahOn = arrayOf("steam.steam_api",
                                     "steam.ISteamUserStats",
                                     "steam.ISteamFriends",
                                     "steam.ISteamUser",
                                     "steam.ISteamUtils",
                                     "steam.steam_gameserver",
                                     "steam.ISteamGameServer",
                                     "steam.ISteamRemoteStorage",
                                     "steam.SteamUserStatsListener",
                                     "steam.ISteamController",
                                     "steam.ISteamApps"
   )
   inputs.dir(sourceSets["main"].java.outputDir)
   inputs.property("classes", classesToRunJavahOn)
   outputs.dir(outputDirectory)

   doLast {
      outputDirectory.parentFile.mkdirs()
      exec {
         executable = Jvm.current().getExecutable("javah").canonicalPath
         args("-d")
         args(outputDirectory.canonicalPath)
         args("-classpath")
         args(sourceSets["main"].output.classesDirs.asPath)
         classesToRunJavahOn.forEach { classToGenerateHeaderFor ->
            args(classToGenerateHeaderFor)
         }

         args.forEach { theArg ->
            logger.info(theArg)
         }
      }

      Files.newDirectoryStream(outputDirectory.toPath()).use { directoryStream ->
         directoryStream.forEach { path ->
            if (!path.fileName.toString().endsWith(".h")) {
               return@forEach
            }
            var inputChannel: SeekableByteChannel? = null
            var outputChannel: SeekableByteChannel? = null
            var foundCr = false
            var outputFile: File? = null
            try {
               inputChannel = Files.newByteChannel(path, StandardOpenOption.READ)
               outputFile =
                     File(path.toFile().absoluteFile.parentFile, "${path.fileName}lf")
               outputChannel =
                     Files.newByteChannel(outputFile.toPath(),
                                          StandardOpenOption.CREATE,
                                          StandardOpenOption.TRUNCATE_EXISTING,
                                          StandardOpenOption.WRITE)
               foundCr = false

               val inBuffer = ByteBuffer.allocate(1024)
               val outBuffer = ByteBuffer.allocate(inBuffer.capacity())
               while (true) {
                  inBuffer.clear()
                  outBuffer.clear()
                  val bytesRead = inputChannel.read(inBuffer)
                  if (bytesRead < 0) {
                     break
                  }
                  inBuffer.flip()
                  while (inBuffer.remaining() > 0) {
                     val b = inBuffer.get()
                     if (b == '\r'.toByte()) {
                        foundCr = true
                     } else {
                        outBuffer.put(b)
                     }
                  }
                  outBuffer.flip()
                  outputChannel.write(outBuffer)
               }
            } catch (throwable: Throwable) {
               logger.error("Failed to remove carriage returns from header files", throwable)
            } finally {
               inputChannel?.close()
               outputChannel?.close()
            }
            if (foundCr) {
               logger.info("Replaced \\r in path = $path")
               Files.move(outputFile?.toPath(), path, StandardCopyOption.REPLACE_EXISTING)
            } else {
               if (Files.exists(outputFile?.toPath())) {
                  Files.delete(outputFile?.toPath())
               }
            }
         }
      }
   }
}

// always run header generation after classes are created
tasks.named("classes") {
   finalizedBy(nativeHeaders)
}

val jniHeaders = tasks.register<Zip>("jniHeaders") {
   dependsOn(nativeHeaders)
   from("$buildDir/steamjni/cpp")
}

artifacts {
   add(configurations.register("jniHeaders").name, jniHeaders)
}