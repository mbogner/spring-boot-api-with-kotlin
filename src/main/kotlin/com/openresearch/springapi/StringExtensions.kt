package com.openresearch.springapi

val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()
fun String.camelToSnakeCase(): String = camelRegex.replace(this) { "_${it.value}" }.lowercase()

val snakeRegex = "_[a-zA-Z]".toRegex()

fun String.removeFirstLastChar(): String = this.substring(1, this.length - 1)

fun String.snakeToLowerCamelCase(): String =
    snakeRegex.replace(this) { it.value.replace("_", "").uppercase() }

fun String.snakeToUpperCamelCase(): String = snakeToLowerCamelCase().replaceFirstChar {
    if (it.isLowerCase()) it.titlecase() else it.toString()
}
