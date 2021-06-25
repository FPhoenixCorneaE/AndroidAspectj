package com.fphoenixcorneae.aspectj.demo

import com.fphoenixcorneae.annotation.AndroidAspectj

@AndroidAspectj("execution(* android.view.View.OnClickListener.onClick(..))")
@Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class OnClick