object Deps {
    /** Android */
    object Android {
        const val buildToolsGradle = "com.android.tools.build:gradle:4.2.1"
        const val compileSdkVersion = 30
        const val buildToolsVersion = "30.0.3"
        const val minSdkVersion = 21
        const val targetSdkVersion = 30
        const val versionCode = 100
        const val versionName = "1.0.0"
    }

    /** BuildType */
    object BuildType {
        const val Debug = "debug"
        const val Release = "release"
    }

    /** Kotlin */
    object Kotlin {
        const val version = "1.5.10"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        const val bom = "org.jetbrains.kotlin:kotlin-bom:$version"
    }

    /** AndroidX */
    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val coreKtx = "androidx.core:core-ktx:1.3.2"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    }

    /** Aspectj */
    object Aspectj {
        const val aspectjRt = "org.aspectj:aspectjrt:1.9.6"
        const val aspectjTools = "org.aspectj:aspectjtools:1.9.6"
    }

    /** Java */
    object Java {
        const val poet = "com.squareup:javapoet:1.13.0"
    }

    /** Test */
    object Test {
        const val junit = "junit:junit:4.13.2"
        const val junitExt = "androidx.test.ext:junit:1.1.2"
        const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
    }
}