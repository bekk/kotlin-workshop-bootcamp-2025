plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.spring") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.veiset.libgdx"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":kordle:shared"))
    implementation(project(":kordle:core"))
    implementation(project(":kordle:lwjgl3"))
    implementation(project(":kordle:wordgeneration"))
    implementation(project(":kordle:server"))
    implementation(project(":introduction"))
}
