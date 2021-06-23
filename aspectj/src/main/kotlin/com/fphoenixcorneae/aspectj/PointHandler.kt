package com.fphoenixcorneae.aspectj

import org.aspectj.lang.ProceedingJoinPoint

/**
 * @desc：点处理程序
 * @date：2021-06-23 16:30
 */
interface PointHandler {
    fun onHandlePoint(cls: Class<*>, joinPoint: ProceedingJoinPoint)
}