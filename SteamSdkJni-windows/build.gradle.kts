/*
 * Copyright (c) 2019 Nimbly Games, LLC all rights reserved
 */

import org.apache.tools.ant.taskdefs.condition.Os

/*
 * Repackage the native jars from SteamSdkJni so they are available to Gradle composite builds. Composite builds to not support classifiers.
 */

group = rootProject.group
version = rootProject.version

plugins {
   `java-library`
   id("com.nimblygames.gradle")
   `maven-publish`
}

java {
   sourceCompatibility = JavaVersion.VERSION_1_8
   targetCompatibility = JavaVersion.VERSION_1_8
}

/**
 * Configuration for exporting native libraries
 */
val steamSdkJniConfiguration = configurations.register("steamSdkJniConfiguration")
dependencies {
   steamSdkJniConfiguration.get()(project(":SteamSdkJni", "platformNativeZip"))
}

tasks.named<ProcessResources>(JavaPlugin.PROCESS_RESOURCES_TASK_NAME) {
   dependsOn(steamSdkJniConfiguration.get())

   steamSdkJniConfiguration.get().forEach { configurationFile ->
      from(zipTree(configurationFile))
   }
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
   if (Os.isFamily(Os.FAMILY_WINDOWS)) {
      publications {
         create<MavenPublication>(project.name) {
            groupId = project.group as String
            version = project.version as String
            artifactId = project.name

            artifact(tasks.getByName(JavaPlugin.JAR_TASK_NAME))
         }
      }
   }
}
