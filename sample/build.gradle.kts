plugins {
    java
    kotlin("jvm")
    kotlin("kapt")
    application
}

application {
    mainClass.set("MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testCompile("junit", "junit", "4.12")

    implementation(project(":annotations"))
    kapt(project(":generator"))
}
