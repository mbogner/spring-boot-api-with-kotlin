package com.openresearch.springapi.error

class ErrorCodeException : RuntimeException {

    val errorCode: ErrorCode
    val args: Map<String, Any?>?

    constructor(errorCode: ErrorCode, message: String) : super(message) {
        this.errorCode = errorCode
        this.args = null
    }

    constructor(errorCode: ErrorCode, message: String, throwable: Throwable) : super(message, throwable) {
        this.errorCode = errorCode
        this.args = null
    }

    constructor(errorCode: ErrorCode, throwable: Throwable) : super(throwable) {
        this.errorCode = errorCode
        this.args = null
    }

    constructor(errorCode: ErrorCode, args: Map<String, Any?>, message: String) : super(message) {
        this.errorCode = errorCode
        this.args = args
    }

    constructor(errorCode: ErrorCode, args: Map<String, Any?>, message: String, throwable: Throwable) : super(
        message,
        throwable
    ) {
        this.errorCode = errorCode
        this.args = args
    }

    constructor(errorCode: ErrorCode, args: Map<String, Any?>, throwable: Throwable) : super(throwable) {
        this.errorCode = errorCode
        this.args = args
    }

}
