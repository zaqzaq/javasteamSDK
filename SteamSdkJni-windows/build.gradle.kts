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

import com.nimblygames.gradle.addPublishRepositories
import org.apache.tools.ant.taskdefs.condition.Os

/*
 * Repackage the native jars from SteamSdkJni so they are available to Gradle composite builds. Composite builds do not support classifiers.
 */

group = rootProject.group
version = rootProject.version

plugins {
   `java-library`
   id("com.nimblygames.gradle")
   `maven-publish`
   signing
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

/**
 * Is the packer version a snapshot or release?
 */
val isSnapshot = project.version.toString().contains("SNAPSHOT")

publishing {
   repositories {
      addPublishRepositories(project, isSnapshot)
   }
   publications {
      register<MavenPublication>(project.name) {
         groupId = project.group as String
         version = project.version as String
         artifactId = "steam-sdk-jni-windows"

         artifact(tasks.getByName(JavaPlugin.JAR_TASK_NAME))

         pom {
            name.set("SteamSdkJni-windows")
            description.set("Native JNI code for Windows.")
            url.set("https://nimblygames.com/")
            licenses {
               license {
                  name.set("The Apache License, Version 2.0")
                  url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
               }
            }
            developers {
               developer {
                  id.set("KarlSabo")
                  name.set("Karl Sabo")
                  email.set("karl@nimblygames.com")
               }
            }
            scm {
               connection.set("scm:git:https://gitlab.com/nimblygames/steam")
               developerConnection.set("scm:git:https://gitlab.com/nimblygames/steam")
               url.set("https://gitlab.com/nimblygames/steam")
            }
         }
      }
   }
}

tasks.withType(AbstractPublishToMaven::class.java).configureEach {
   if (!Os.isFamily(Os.FAMILY_WINDOWS)) {
      enabled = false
   }
}


signing.useGpgCmd()

if (isSnapshot) {
   logger.info("Skipping signing")
} else {
   publishing.publications.configureEach {
      logger.info("Should sign publication ${this.name}")
      signing.sign(this)
   }
}
