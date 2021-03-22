plugins {
    java
    kotlin("jvm")
    `maven-publish`
}

group = "dev.riajul.fastcsv.codegen"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    api("de.siegmar:fastcsv:1.0.4")

}
