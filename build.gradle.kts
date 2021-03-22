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
    repositories {
        google()
        jcenter()
    }
}

task("clean") {
    delete(rootProject.buildDir)
}