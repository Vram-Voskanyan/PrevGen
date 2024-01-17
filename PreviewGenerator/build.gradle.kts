plugins {
    kotlin("jvm")
}

group = "com.prevgen"
version = "0.1-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.21-1.0.15")
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}