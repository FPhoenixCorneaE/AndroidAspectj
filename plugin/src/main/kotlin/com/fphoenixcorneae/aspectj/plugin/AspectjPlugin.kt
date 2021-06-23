package com.fphoenixcorneae.aspectj.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.BaseVariant
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.DomainObjectSet
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

import org.gradle.api.logging.Logger
import org.gradle.api.tasks.compile.JavaCompile
import java.io.File

/**
 * @desc：AspectjPlugin
 * @date：2021-06-23 10:50
 */
class AspectjPlugin : Plugin<Project> {

    companion object {
        const val GRADLE_MISSING_PLUGIN_ERROR =
            "android-aspectj: the 'com.android.application' or 'com.android.library' plugin is required."
        const val CMD_SHOW_WEAVE_INFO = "-showWeaveInfo"
        const val CMD_JAVA_1_8 = "-1.8"
        const val CMD_IN_PATH = "-inpath"
        const val CMD_ASPECT_PATH = "-aspectpath"
        const val CMD_OUTPUT_DIR = "-d"
        const val CMD_CLASS_PATH = "-classpath"
        const val CMD_BOOT_CLASS_PATH = "-bootclasspath"
    }

    override fun apply(project: Project) {
        val hasApp = project.plugins.hasPlugin(AppPlugin::class.java)
        val hasLib = project.plugins.hasPlugin(LibraryPlugin::class.java)
        if (hasApp.not() && hasLib.not()) {
            throw GradleException(GRADLE_MISSING_PLUGIN_ERROR)
        }

        val log: Logger = project.logger
        log.warn("Aspectj Plugin Started!")
        val variants: DomainObjectSet<out BaseVariant>? = if (hasApp) {
            project.extensions.findByType(AppExtension::class.java)?.applicationVariants
        } else {
            project.extensions.findByType(LibraryExtension::class.java)?.libraryVariants
        }

        variants?.all {
            val javaCompile: JavaCompile = javaCompileProvider.get()
            javaCompile.doLast {
                val args = arrayOf(
                    CMD_SHOW_WEAVE_INFO,
                    CMD_JAVA_1_8,
                    CMD_IN_PATH, javaCompile.destinationDir.toString(),
                    CMD_ASPECT_PATH, javaCompile.classpath.asPath,
                    CMD_OUTPUT_DIR, javaCompile.destinationDir.toString(),
                    CMD_CLASS_PATH, javaCompile.classpath.asPath,
                    CMD_BOOT_CLASS_PATH, project.run {
                        if (hasApp) {
                            project.extensions.findByType(AppExtension::class.java)
                                ?.bootClasspath?.joinToString(File.pathSeparator)
                        } else {
                            project.extensions.findByType(LibraryExtension::class.java)
                                ?.bootClasspath?.joinToString(File.pathSeparator)
                        }
                    }
                )
                log.lifecycle("ajc args: $args")

                val messageHandler = MessageHandler(true)
                Main().run(args, messageHandler)
                messageHandler.getMessages(null, true).forEach { message ->
                    when (message.kind) {
                        IMessage.ABORT, IMessage.ERROR, IMessage.FAIL -> {
                            log.error(message.message, message.thrown)
                        }
                        IMessage.WARNING -> {
                            log.warn(message.message, message.thrown)
                        }
                        IMessage.INFO -> {
                            log.info(message.message, message.thrown)
                        }
                        IMessage.DEBUG -> {
                            log.debug(message.message, message.thrown)
                        }
                    }
                }
            }
        }
    }
}