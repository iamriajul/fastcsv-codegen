plugins {
    java
    kotlin("jvm")
    kotlin("kapt")
    `maven-publish`
}

group = "dev.riajul.fastcsv.codegen"
version = "0.1"

kapt {
    generateStubs = true
}

sourceSets {
    main {
        java {
            srcDir("${buildDir.absolutePath}/tmp/kapt/main/kotlinGenerated/")
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":annotations"))

    implementation("com.squareup:kotlinpoet:1.7.2")

    implementation(kotlin("reflect"))

    implementation("com.google.auto.service:auto-service:1.0-rc4")
    kapt("com.google.auto.service:auto-service:1.0-rc4")
}
