plugins {
    `java-library`
    java
    kotlin("jvm")
    `maven-publish`
}

group = "dev.riajul.fastcsv-codegen"
version = "0.1"

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "dev.riajul.fastcsv-codegen"
            artifactId = "annotations"
            version = "0.1"

            from(components["java"])
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    api("de.siegmar:fastcsv:1.0.4")

}
