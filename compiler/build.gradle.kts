plugins {
    id("java-library")
    id("kotlin")
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.Poet.kotlin)
    implementation(Deps.Aspectj.aspectjRt)
    implementation(project(mapOf("path" to ":annotation")))
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
    (this as ExtensionAware).extensions.configure<PublishingExtension>("publishing") {
        publications {
            // Creates a Maven publication called "mavenJava".
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                groupId = "com.github.FPhoenixCorneaE"
                artifactId = "aspectj-compiler"
                version = project.version.toString()
            }
        }
    }
}