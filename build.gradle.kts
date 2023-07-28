plugins {
    kotlin("jvm") version "1.7.10"
}

group = "org.veiset.libgdx"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val gdxVersion = "1.12.0"
val ktxVersion = "1.11.0-rc6"

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.badlogicgames.gdx:gdx:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-lwjgl3-glfw-awt-macos:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-freetype:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-desktop")
    implementation("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop")
    implementation("io.github.libktx:ktx-app:$ktxVersion")
    implementation("io.github.libktx:ktx-graphics:$ktxVersion")
    implementation("io.github.libktx:ktx-box2d:$ktxVersion")
}
