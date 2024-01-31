import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

group = "io.github.vram-voskanyan.kmp"
version = "0.0.2"

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
                implementation("com.google.devtools.ksp:symbol-processing-api:1.9.21-1.0.15")
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
        }
    }
}
