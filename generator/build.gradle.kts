import java.net.URI

plugins {
    `java-library`
    java
    kotlin("jvm")
    kotlin("kapt")
    `maven-publish`
    signing
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

    compileOnly("net.ltgt.gradle.incap:incap:0.3")
    annotationProcessor("net.ltgt.gradle.incap:incap-processor:0.3")

}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = group.toString()
            version = version
            artifactId = "generator"

            from(components["java"])

            pom {
                name.set("FastCSV-Codegen")
                description.set("Ultra Fast Csv Deserialization to Data Class based on Code Generations.")
                url.set("https://github.com/iamriajul/fastcsv-codegen")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                scm {
                    url.set("https://github.com/iamriajul/fastcsv-codegen")
                    developerConnection.set("scm:git:https://github.com/iamriajul/fastcsv-codegen.git")
                    connection.set("scm:git:https://github.com/iamriajul/fastcsv-codegen.git")
                }

                developers {
                    developer {
                        id.set("iamriajul")
                        name.set("Riajul Islam")
                        email.set("kmriajulislami@gmail.com")
                    }
                }

            }
        }
    }

    repositories {

        maven {
            name = "ossrh"

            credentials(PasswordCredentials::class)

            val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"

            url = URI.create(if(version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}