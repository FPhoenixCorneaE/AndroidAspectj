package com.fphoenixcorneae.annotation

/**
 * @desc：Usage：在想要拦截的 annotation class 之上添加 @AndroidAspectj 注解
 * @date：2021-06-23 15:08
 */
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class AndroidAspectj(
    val value: String = "",
)