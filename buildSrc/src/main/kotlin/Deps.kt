object Deps {
    /** Android */
    object Android {
        const val buildToolsGradle = "com.android.tools.build:gradle:7.0.3"
        const val compileSdkVersion = 31
        const val buildToolsVersion = "31.0.0"
        const val minSdkVersion = 21
        const val targetSdkVersion = 31
        const val versionCode = 107
        const val versionName = "1.0.7"
    }

    object FPhoenixCorneaE {
        private const val version = Android.versionName
        const val aspectj = "com.github.FPhoenixCorneaE.AndroidAspectj:aspectj:${version}"
        const val aspectjPlugin = "com.github.FPhoenixCorneaE.AndroidAspectj:aspectj-plugin:${version}"
        const val aspectjCompiler = "com.github.FPhoenixCorneaE.AndroidAspectj:aspectj-compiler:${version}"
        const val localAspectjPlugin = "AndroidAspectj:plugin:${version}"
    }

    /** AspectjPlugin */
    object AspectjPlugin {
        const val name = "AndroidAspectj"
        const val id = "com.FPhoenixCorneaE.aspectj"
        const val implementationClass = "com.fphoenixcorneae.aspectj.plugin.AspectjPlugin"
        const val displayName = "android aspectj gradle plugin."
        const val description = "a gradle plugin for android aspectj."
        const val version = Android.versionName
    }

    /** BuildType */
    object BuildType {
        const val Debug = "debug"
        const val Release = "release"
    }

    /** Kotlin */
    object Kotlin {
        private const val version = "1.6.0"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
    }

    /** AndroidX */
    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.4.0"
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.2"
    }

    /** Aspectj */
    object Aspectj {
        const val aspectjRt = "org.aspectj:aspectjrt:1.9.6"
        const val aspectjTools = "org.aspectj:aspectjtools:1.9.6"
    }

    /** Poet */
    object Poet {
        const val kotlin = "com.squareup:kotlinpoet:1.9.0"
    }

    /** Test */
    object Test {
        const val junit = "junit:junit:4.13.2"
        const val junitExt = "androidx.test.ext:junit:1.1.2"
        const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
    }
}