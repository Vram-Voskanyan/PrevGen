import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
//    kotlin("jvm")
    kotlin("multiplatform")
    id("maven-publish")
    id("com.android.library")
}

group = "io.github.vram-voskanyan"
version = "0.0.4.1-SNAPSHOT"

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["kotlin"])
        }
    }

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
    androidTarget {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {}

//    cocoapods {
//        summary = "Some description for the Shared Module"
//        homepage = "Link to the Shared Module homepage"
//        ios.deploymentTarget = "14.1"
//        frameworkName = "shared"
//        podfile = project.file("../iosApp/Podfile")
//    }

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

//dependencies {
//    implementation(kotlin("stdlib"))
//    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.21-1.0.15")
//}
//
//sourceSets.main {
//    java.srcDirs("src/main/kotlin")
//}
