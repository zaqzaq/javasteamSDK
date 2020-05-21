/*
 * Copyright (c) 2019 Nimbly Games, LLC all rights reserved
 */

import com.nimblygames.gradle.junitVersion
import com.nimblygames.gradle.log4jVersion
import com.nimblygames.gradle.slf4jVersion

group = rootProject.group
version = rootProject.version

plugins {
   `java-library`
   id("com.nimblygames.gradle")
   `maven-publish`
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

   runtimeOnly(project(":SteamSdkJni-windows"))
   runtimeOnly(project(":SteamSdkJni-linux"))
   runtimeOnly(project(":SteamSdkJni-macos"))

   // logging
   implementation("org.slf4j:slf4j-api:$slf4jVersion")
   runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")
   runtimeOnly("org.apache.logging.log4j:log4j-core:$log4jVersion")
}

tasks.withType(Test::class.java) {
   maxParallelForks = 1
}

/**
 * Directory that generated header files are saved during java compile.
 */
val jniHeaderGenerationDirectory = file("$buildDir/steamjni/cpp")

/**
 * The Java compile task. Updated to produce native header files in [jniHeaderGenerationDirectory].
 */
val compileJava = tasks.named<JavaCompile>(JavaPlugin.COMPILE_JAVA_TASK_NAME) {
   this.options.headerOutputDirectory.set(jniHeaderGenerationDirectory)
}

/**
 * Zips all files in [jniHeaderGenerationDirectory] for consumption as part of the `jniHeaders` configuration.
 */
val zipJniHeaders = tasks.register<Zip>("zipJniHeaders") {
   dependsOn(compileJava)
   from("$buildDir/steamjni/cpp")
}

artifacts {
   add(configurations.register("jniHeaders").name, zipJniHeaders)
}

publishing {
   repositories {
      maven {
         url = uri("https://artifactory.nimblygames.com/artifactory/gradle-release-local/")
         credentials {
            username = rootProject.findProperty("artifactServerUsername") as String
            password = rootProject.findProperty("artifactServerPassword") as String
         }
      }
   }
   publications {
      create<MavenPublication>(project.name) {
         groupId = project.group as String
         version = project.version as String

         artifact(tasks.named(JavaPlugin.JAR_TASK_NAME).get())
      }
   }
}