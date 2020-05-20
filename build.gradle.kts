/*
 * Copyright (c) 2020 Nimbly Games, LLC all rights reserved
 */

group = "com.nimblygames.steam"
version = "1.1.0-SNAPSHOT"

plugins {
   id("com.nimblygames.gradle")
}

repositories {
   jcenter()
}

tasks.named<Wrapper>("wrapper") {
   gradleVersion = "6.4.1"
   distributionType = Wrapper.DistributionType.ALL
}
