plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    `maven-publish`
}

android {
    compileSdkVersion(Deps.Android.compileSdkVersion)
    buildToolsVersion(Deps.Android.buildToolsVersion)

    defaultConfig {
        minSdkVersion(Deps.Android.minSdkVersion)
        targetSdkVersion(Deps.Android.targetSdkVersion)
        versionCode = Deps.Android.versionCode
        versionName = Deps.Android.versionName

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
        setConsumerProguardFiles(listOf("consumer-rules.pro"))
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
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    dexOptions {
        jumboMode = true
    }

    lintOptions {
        isCheckReleaseBuilds = false
        isAbortOnError = false
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.coreKtx)
    api(Deps.Aspectj.aspectjRt)
    api(project(mapOf("path" to ":annotation")))
    // test
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.junitExt)
    androidTestImplementation(Deps.Test.espresso)
}

// MavenPublication 配置-------------------------------------------------------------

afterEvaluate {
    (this as ExtensionAware).extensions.configure<PublishingExtension>("publishing") {
        publications {
            // Creates a Maven publication called "release".
            create<MavenPublication>(Deps.BuildType.Release) {
                from(components[Deps.BuildType.Release])
                groupId = "com.github.FPhoenixCorneaE"
                artifactId = "aspectj"
                version = project.version.toString()
            }
        }
    }
}