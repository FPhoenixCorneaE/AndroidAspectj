plugins {
    `kotlin-dsl`
    `maven-publish`
}

dependencies {
    implementation(Deps.Android.buildToolsGradle)
    implementation(Deps.Kotlin.gradlePlugin)
    implementation(Deps.Aspectj.aspectjRt)
    implementation(Deps.Aspectj.aspectjTools)
}

gradlePlugin {
    plugins {
        create(Deps.GradlePlugin.name) {
            // 自定义 plugin 的 id，其他 module 引用要用到
            id = Deps.GradlePlugin.id
            // 实现这个插件的类的路径
            implementationClass = Deps.GradlePlugin.implementationClass
            displayName = Deps.GradlePlugin.displayName
            description = Deps.GradlePlugin.description
            version = Deps.GradlePlugin.version
        }
    }
}
