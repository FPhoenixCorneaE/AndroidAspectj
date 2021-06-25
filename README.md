# AndroidAspectj
Android 面向切面编程，登录状态拦截、日志拦截、权限拦截等等，轻松完成各种骚操作！

[![](https://jitpack.io/v/FPhoenixCorneaE/AndroidAspectj.svg)](https://jitpack.io/#FPhoenixCorneaE/AndroidAspectj)


How to include it in your project:
--------------
**Step 1.** Add the JitPack repository to your project's build.gradle.kts file.

```kotlin
buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("org.aspectj:aspectjtools:1.9.6")
        classpath("com.github.FPhoenixCorneaE.AndroidAspectj:aspectj-plugin:latest")
    }
}
allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

**Step 2.** Add the plugin and dependency to your application's build.gradle.kts file or library's build.gradle.kts file.
```kotlin
plugins {
    kotlin("kapt")
    id("com.fphoenixcorneae.aspectj")
}

dependencies {
	implementation("com.github.FPhoenixCorneaE.AndroidAspectj:aspectj:latest")
    kapt("com.github.FPhoenixCorneaE.AndroidAspectj:aspectj-compiler:latest")
}
```

How to use:
--------------
**Step 1.** Set Aspectj Pointcut intercept handle.

```kotlin
        AspectjHandler.init { cls, joinPoint ->
            Log.d(
                "AndroidAspectj",
                "notifyHandler() called with: clazz = [$cls], joinPoint = [$joinPoint]"
            )
            try {
                when (cls) {
                    MustLogin::class.java -> {
                        Log.d("AndroidAspectj", "@MustLogin annotation method call")
                    }
                    else -> {
                        joinPoint.proceed()
                    }
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
        }
```

**Step 2.** Add @AndroidAspectj to your custom annotation class.

```kotlin
@AndroidAspectj
@Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class MustLogin
```

**Step 3.** Add your custom annotation to methods what you would intercept.

```kotlin
    @MustLogin
    fun giveALike() {
        Toast.makeText(this, "点赞成功", Toast.LENGTH_SHORT).show()
    }
```