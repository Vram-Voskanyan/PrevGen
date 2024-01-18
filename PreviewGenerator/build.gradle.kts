plugins {
    kotlin("jvm")
    id("maven-publish")
}

group = "io.github.vram-voskanyan"
version = "0.0.1-SNAPSHOT"

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            // Sonatype Nexus URL
            // TODO: Add release url, with -SNAPSHOT check
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            credentials {
                username = project.findProperty("ossrhUsername").toString()
                password = project.findProperty("ossrhPassword").toString()
            }
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.21-1.0.15")
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}
