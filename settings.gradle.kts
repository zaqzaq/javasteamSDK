/*
 * Copyright (c) 2019 Nimbly Games, LLC all rights reserved
 */
val artifactory_user: String? by settings
val artifactory_password: String? by settings

pluginManagement {
   repositories {
      maven {
         url = uri("https://artifactory.nimblygames.com/artifactory/gradle-release-local/")
         credentials {

            username = artifactory_user
            password = artifactory_password
         }
      }

      gradlePluginPortal()
      jcenter()
   }


   resolutionStrategy {
      eachPlugin {
         if (requested.id.id == "com.nimblygames.gradle") {
            useModule("com.nimblygames:GradlePlugin:1.0.7")
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