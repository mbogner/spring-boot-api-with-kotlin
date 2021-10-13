package com.openresearch.springapi.logging

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LoggedMethod(
    val mask: Array<String> = []
)
