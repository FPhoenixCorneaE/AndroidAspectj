// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
//        maven("/repos")
    }
    dependencies {
        classpath(Deps.Android.buildToolsGradle)
        classpath(Deps.Kotlin.gradlePlugin)
        classpath(Deps.Aspectj.aspectjTools)
//        classpath(Deps.FPhoenixCorneaE.aspectjPlugin)
//        classpath(Deps.FPhoenixCorneaE.localAspectjPlugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}