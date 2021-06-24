package com.fphoenixcorneae.aspectj

import org.aspectj.lang.ProceedingJoinPoint

/**
 * @desc：通知点处理程序
 * @date：2021-06-23 16:36
 */
object PhoenixAspectj {

    @Volatile
    private var mPointHandler: ((Class<*>, ProceedingJoinPoint) -> Unit)? = null

    @Synchronized
    fun init(pointHandler: ((cls: Class<*>, joinPoint: ProceedingJoinPoint) -> Unit)? = null) {
        mPointHandler = pointHandler
    }

    @Synchronized
    fun notifyHandler(cls: Class<*>, joinPoint: ProceedingJoinPoint) {
        mPointHandler?.invoke(cls, joinPoint)
    }
}