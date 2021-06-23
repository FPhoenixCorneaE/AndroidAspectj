// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(Deps.Android.buildToolsGradle)
        classpath(Deps.Kotlin.gradlePlugin)
        classpath(Deps.Aspectj.aspectjTools)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}