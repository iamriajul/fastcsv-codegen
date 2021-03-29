buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath (kotlin("gradle-plugin", "1.4.31"))
    }
}

allprojects {

    group = "dev.riajul.fastcsv-codegen"
    version = "1.0-SNAPSHOT.1"

    repositories {
        google()
        jcenter()
    }
}

task("clean") {
    delete(rootProject.buildDir)

    allprojects {
        delete(this.buildDir)
    }
}