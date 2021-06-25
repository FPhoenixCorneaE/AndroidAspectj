plugins {
    id("java-library")
    `kotlin-dsl`
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(Deps.Android.buildToolsGradle)
    implementation(Deps.Kotlin.gradlePlugin)
    implementation(Deps.Aspectj.aspectjRt)
    implementation(Deps.Aspectj.aspectjTools)
}

gradlePlugin {
    plugins {
        create(Deps.AspectjPlugin.name) {
            // 自定义 plugin 的 id，其他 module 引用要用到
            id = Deps.AspectjPlugin.id
            // 实现这个插件的类的路径
            implementationClass = Deps.AspectjPlugin.implementationClass
            displayName = Deps.AspectjPlugin.displayName
            description = Deps.AspectjPlugin.description
            version = Deps.AspectjPlugin.version
        }
    }
}

// MavenPublication 配置-------------------------------------------------------------

// 指定编码
tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
}
// 打包源码
task("sourcesJar", Jar::class) {
    from("src/main/kotlin")
    archiveClassifier.convention("sources")
    archiveClassifier.set("sources")
}

// 制作文档(Javadoc)
task("javadocJar", Jar::class) {
    archiveClassifier.convention("javadoc")
    archiveClassifier.set("javadoc")
    val javadoc = tasks.getByName("javadoc") as Javadoc
    from(javadoc.destinationDir)
}

artifacts {
    val sourcesJar = tasks.getByName("sourcesJar")
    val javadocJar = tasks.getByName("javadocJar")
    archives(sourcesJar)
    archives(javadocJar)
}

afterEvaluate {
    publishing {
        publications {
            // Creates a Maven publication called "mavenJava".
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                groupId = "com.github.FPhoenixCorneaE"
                artifactId = "aspectj-plugin"
                version = project.version.toString()
            }
        }
//        repositories {
//            maven {
//                url = uri("../repos")
//            }
//        }
    }
}

