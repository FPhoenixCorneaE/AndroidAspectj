package com.fphoenixcorneae.aspectj

import org.aspectj.lang.ProceedingJoinPoint

/**
 * @desc：通知点处理程序
 * @date：2021-06-23 16:36
 */
object PhoenixAspectj {

    @Volatile
    private var sHandler: PointHandler? = null

    @Synchronized
    fun init(pointHandler: PointHandler) {
        sHandler = pointHandler
    }

    @Synchronized
    fun notifyHandler(cls: Class<*>, joinPoint: ProceedingJoinPoint) {
        sHandler?.onHandlePoint(cls, joinPoint)
    }
}