plugins {
    kotlin("plugin.serialization") version "2.1.0"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${project.property("kotlinVersion")}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.9.0")
}
