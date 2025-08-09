buildscript {
  repositories {
    mavenCentral()
    maven { url = uri("https://s01.oss.sonatype.org") }
    gradlePluginPortal()
    mavenLocal()
    google()
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/") }
  }
  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${property("kotlinVersion")}")
  }
}

allprojects {
  apply(plugin = "eclipse")
  apply(plugin = "idea")

  // This allows you to "Build and run using IntelliJ IDEA", an option in IDEA's Settings.
  configure<org.gradle.plugins.ide.idea.model.IdeaModel> {
    module {
      outputDir = file("build/classes/java/main")
      testOutputDir = file("build/classes/java/test")
    }
  }
}

configure(subprojects) {
  apply(plugin = "java-library")
  apply(plugin = "kotlin")

  configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_21
  }

  // From https://lyze.dev/2021/04/29/libGDX-Internal-Assets-List/
  // The article can be helpful when using assets.txt in your project.
  tasks.register("generateAssetList") {
    inputs.dir("${project.rootDir}/assets/")
    doLast {
      // projectFolder/assets
      val assetsFolder = File("${project.rootDir}/assets/")
      // projectFolder/assets/assets.txt
      val assetsFile = File(assetsFolder, "assets.txt")
      // delete that file in case we've already created it
      assetsFile.delete()

      // iterate through all files inside that folder
      // convert it to a relative path
      // and append it to the file assets.txt
      fileTree(assetsFolder).map { assetsFolder.relativeTo(it) }.sorted().forEach {
        assetsFile.appendText("$it\n")
      }
    }
  }

  tasks.named("processResources") {
    dependsOn("generateAssetList")
  }

  tasks.withType<JavaCompile> {
    options.isIncremental = true
  }

  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
      jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
  }
}

subprojects {
  version = property("projectVersion").toString()
  extra["appName"] = "Kordle"

  repositories {
    mavenCentral()
    maven { url = uri("https://s01.oss.sonatype.org") }
    // You may want to remove the following line if you have errors downloading dependencies.
    mavenLocal()
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/") }
    maven { url = uri("https://jitpack.io") }
  }
}

configure<org.gradle.plugins.ide.eclipse.model.EclipseModel> {
  project {
    name = "Kordle-parent"
  }
}
