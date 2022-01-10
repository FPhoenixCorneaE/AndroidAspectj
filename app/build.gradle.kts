plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
//    id("com.FPhoenixCorneaE.aspectj")
}

android {
    compileSdk = Deps.Android.compileSdkVersion
    buildToolsVersion = Deps.Android.buildToolsVersion

    defaultConfig {
        applicationId = "com.fphoenixcorneae.aspectj.demo"
        minSdk = Deps.Android.minSdkVersion
        targetSdk = Deps.Android.targetSdkVersion
        versionCode = Deps.Android.versionCode
        versionName = Deps.Android.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName(Deps.BuildType.Release) {
            // 执行proguard混淆
            isMinifyEnabled = false
            // 移除无用的resource文件
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName(Deps.BuildType.Debug) {
            // 执行proguard混淆
            isMinifyEnabled = false
            // 移除无用的resource文件
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets {
        val main by getting
        main.java.srcDirs("src/main/kotlin")
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_11
        sourceCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    lint {
        isCheckReleaseBuilds = false
        isAbortOnError = false
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.constraintLayout)
    implementation(Deps.AndroidX.coreKtx)
//    implementation(Deps.FPhoenixCorneaE.aspectj)
//    kapt(Deps.FPhoenixCorneaE.aspectjCompiler)
    implementation(project(mapOf("path" to ":aspectj")))
    kapt(project(mapOf("path" to ":compiler")))
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.junitExt)
    androidTestImplementation(Deps.Test.espresso)
}