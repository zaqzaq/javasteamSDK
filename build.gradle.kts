/*
 * Copyright (c) 2019 Nimbly Games, LLC all rights reserved
 */

/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

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
