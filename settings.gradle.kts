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

pluginManagement {
   fun findProperty(property: String): String? {
      return try {
         settings.javaClass.getMethod("getProperty", String::class.java).invoke(settings, property) as String?
      } catch (exception: Throwable) {
         null
      }
   }

   fun hasProperty(property: String): Boolean {
      return findProperty(property) != null
   }

   repositories {
      for (repositoryIndex in 0..10) {
         if (hasProperty("maven.repository.url.$repositoryIndex") && findProperty("maven.repository.isdownload.$repositoryIndex").toString().toBoolean()) {
            maven {
               url = uri(findProperty("maven.repository.url.$repositoryIndex") as String)
               if (hasProperty("maven.repository.username.$repositoryIndex")) {
                  credentials {
                     username = findProperty("maven.repository.username.$repositoryIndex") as String
                     password = findProperty("maven.repository.password.$repositoryIndex") as String
                  }
               }
            }
         }
      }

      gradlePluginPortal()
      jcenter()
   }

   resolutionStrategy {
      eachPlugin {
         if (requested.id.id == "com.nimblygames.gradle") {
            useModule("com.nimblygames.gradle:GradlePlugin:1.0.8-SNAPSHOT")
         }
      }
   }
}

rootProject.name = "steam"

include("SteamSdk")
include("SteamSdkJni")
include("SteamSdkJni-linux")
include("SteamSdkJni-macos")
include("SteamSdkJni-windows")
