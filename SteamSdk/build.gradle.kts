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

import com.nimblygames.gradle.addNimblyGamesPublishRepositories
import com.nimblygames.gradle.junitVersion
import com.nimblygames.gradle.log4jVersion
import com.nimblygames.gradle.slf4jVersion

group = rootProject.group
version = rootProject.version

plugins {
   `java-library`
   id("com.nimblygames.gradle")
   `maven-publish`
   signing
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

/**
 * Is the packer version a snapshot or release?
 */
val isSnapshot = project.version.toString().contains("SNAPSHOT")

publishing {
   repositories {
      addNimblyGamesPublishRepositories(project, isSnapshot)
   }
   publications {
      register<MavenPublication>(project.name) {
         groupId = project.group as String
         version = project.version as String

         from(components["java"])
         artifactId = project.name.toLowerCase()

         pom {
            name.set("SteamSdk")
            description.set("JNI wrappers around some of the SteamWorks C++ API.")
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

signing.useGpgCmd()

if (isSnapshot) {
   logger.info("Skipping signing")
} else {
   publishing.publications.configureEach {
      logger.info("Should sign publication ${this.name}")
      signing.sign(this)
   }
}
