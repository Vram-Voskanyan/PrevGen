plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

group = "io.github.vram-voskanyan.kmp"
version = "1.0.1"

publishing {

    repositories {
        maven {
            url = if (!project.version.toString().endsWith("SNAPSHOT")) {
                uri("https://s01.oss.sonatype.org/content/repositories/releases/")
            } else {
                uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            }
            credentials {
                username = project.findProperty("ossrhUsername").toString()
                password = project.findProperty("ossrhPassword").toString()
            }
        }
    }
}

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(libs.symbol.processing.api)
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
        }
    }
}
