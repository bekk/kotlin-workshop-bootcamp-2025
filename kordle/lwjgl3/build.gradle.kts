import io.github.fourlastor.construo.Target

buildscript {
  repositories {
    gradlePluginPortal()
  }
  dependencies {
    classpath("io.github.fourlastor:construo:1.6.1")
    if (project.property("enableGraalNative") == "true") {
      classpath("org.graalvm.buildtools.native:org.graalvm.buildtools.native.gradle.plugin:0.9.28")
    }
  }
}

plugins {
  application
    id("io.github.fourlastor.construo") version "1.7.1"
}

apply(plugin = "io.github.fourlastor.construo")
apply(plugin = "org.jetbrains.kotlin.jvm")

sourceSets {
  main {
    resources.srcDirs(rootProject.file("assets").path)
  }
}

val mainClassName = "no.bekk.kordle.lwjgl3.Lwjgl3Launcher"
application {
  mainClass.set(mainClassName)
}

java {
  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21
}

if (JavaVersion.current().isJava9Compatible) {
  tasks.withType<JavaCompile> {
    options.release.set(21)
  }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  compilerOptions {
    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
  }
}

dependencies {
  implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:${project.property("gdxVersion")}")
  implementation("com.badlogicgames.gdx:gdx-box2d-platform:${project.property("gdxVersion")}:natives-desktop")
  implementation("com.badlogicgames.gdx:gdx-freetype-platform:${project.property("gdxVersion")}:natives-desktop")
  implementation("com.badlogicgames.gdx:gdx-platform:${project.property("gdxVersion")}:natives-desktop")
  implementation(project(":kordle:core"))

  if (project.property("enableGraalNative") == "true") {
    implementation("io.github.berstanio:gdx-svmhelper-backend-lwjgl3:${project.property("graalHelperVersion")}")
  }
}

val os = System.getProperty("os.name").lowercase()

tasks.named<JavaExec>("run") {
  workingDir = rootProject.file("assets")
  // You can uncomment the next line if your IDE claims a build failure even when the app closed properly.
  // isIgnoreExitValue = true

  if (os.contains("mac")) {
    jvmArgs("-XstartOnFirstThread")
  }
}

tasks.named<Jar>("jar") {
  // sets the name of the .jar file this produces to the name of the game or app, with the version after.
  archiveFileName.set("${project.property("appName")}-${project.property("projectVersion")}.jar")
  // the duplicatesStrategy matters starting in Gradle 7.0; this setting works.
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
  dependsOn(configurations.runtimeClasspath)
  from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
  // these "exclude" lines remove some unnecessary duplicate files in the output JAR.
  exclude("META-INF/INDEX.LIST", "META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
  exclude("META-INF/INDEX.LIST", "META-INF/maven/**")
  // setting the manifest makes the JAR runnable.
  manifest {
    attributes["Main-Class"] = mainClassName
  }
  // this last step may help on some OSes that need extra instruction to make runnable JARs.
  doLast {
    archiveFile.get().asFile.setExecutable(true, false)
  }
}

construo {
  // name of the executable
  name.set(project.property("appName").toString())
  // human-readable name, used for example in the `.app` name for macOS
  humanName.set(project.property("appName").toString())
  // Optional, defaults to project version property
  version.set(project.property("projectVersion").toString())

  targets{
    create("linuxX64", Target.Linux::class) {
      architecture.set(Target.Architecture.X86_64)
      jdkUrl.set("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.12%2B7/OpenJDK17U-jdk_x64_linux_hotspot_17.0.12_7.tar.gz")
    }
    create("macM1", Target.MacOs::class) {
      architecture.set(Target.Architecture.AARCH64)
      jdkUrl.set("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.12%2B7/OpenJDK17U-jdk_aarch64_mac_hotspot_17.0.12_7.tar.gz")
      // macOS needs an identifier
      identifier.set("no.bekk.kordle.${project.property("appName")}")
      // Optional: icon for macOS
      macIcon.set(project.file("icons/logo.icns"))
    }
    create("macX64", Target.MacOs::class) {
      architecture.set(Target.Architecture.X86_64)
      jdkUrl.set("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.12%2B7/OpenJDK17U-jdk_x64_mac_hotspot_17.0.12_7.tar.gz")
      // macOS needs an identifier
      identifier.set("no.bekk.kordle.${project.property("appName")}")
      // Optional: icon for macOS
      macIcon.set(project.file("icons/logo.icns"))
    }
    create("winX64", Target.Windows::class) {
      architecture.set(Target.Architecture.X86_64)
      jdkUrl.set("https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.12%2B7/OpenJDK17U-jdk_x64_windows_hotspot_17.0.12_7.zip")
      // Uncomment the next line to show a console when the game runs, to print messages.
      // useConsole.set(true)
    }
  }
}

// Equivalent to the jar task; here for compatibility with gdx-setup.
tasks.register("dist") {
  dependsOn("jar")
}

distributions {
  main {
    contents {
      into("libs") {
        configurations.runtimeClasspath.get().files.filter { file ->
          file.name != tasks.jar.get().outputs.files.singleFile.name
        }.forEach { file ->
          exclude(file.name)
        }
      }
    }
  }
}

tasks.named("startScripts") {
  dependsOn(":lwjgl3:jar")
}

tasks.withType<CreateStartScripts> {
  classpath = tasks.jar.get().outputs.files
}

if (project.property("enableGraalNative") == "true") {
  apply(from = "nativeimage.gradle")
}
