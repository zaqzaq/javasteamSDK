/*
 * Copyright (c) 2019 Nimbly Games, LLC all rights reserved
 */

/*
 * Copyright (c) 2019 Nimbly Games, LLC all rights reserved
 */

group = "com.nimblygames.steam"
version = "1.0.1"

plugins {
   id("com.nimblygames.gradle")
}

repositories {
   jcenter()
}

tasks.named<Wrapper>("wrapper") {
   gradleVersion = "5.4.1"
   distributionType = Wrapper.DistributionType.ALL
}
