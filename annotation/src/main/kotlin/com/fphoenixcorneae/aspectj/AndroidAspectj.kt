package com.fphoenixcorneae.aspectj

/**
 * @desc：Usage：在想要拦截的注解之上添加 @PhoenixAspectj 注解
 * @date：2021-06-23 15:08
 */
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class AndroidAspectj(
    val value: String = "",
)