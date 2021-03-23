import java.net.URI

plugins {
    `java-library`
    java
    kotlin("jvm")
    `maven-publish`
    signing
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    api("de.siegmar:fastcsv:1.0.4")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = group.toString()
            version = version
            artifactId = "annotations"

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