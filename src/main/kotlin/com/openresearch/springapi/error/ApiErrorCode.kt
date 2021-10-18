package com.openresearch.springapi.error

import org.springframework.http.HttpStatus

enum class ApiErrorCode(
    private val code: Int,
    private val status: HttpStatus,
) : ErrorCode {

    // please follow this template with new error codes - camel case all uppercase names,
    // code validated by validator and a proper http code an api should deliver for the error.
    GENERIC_ERROR(
        ApiErrorCodeValidator.validateCode(100000),
        HttpStatus.INTERNAL_SERVER_ERROR
    ),
    BAD_REQUEST(
        ApiErrorCodeValidator.validateCode(100001),
        HttpStatus.BAD_REQUEST
    ),
    MISSING_TENANT_HEADER(
        ApiErrorCodeValidator.validateCode(100002),
        HttpStatus.BAD_REQUEST
    ),
    NOT_FOUND(
        ApiErrorCodeValidator.validateCode(100003),
        HttpStatus.NOT_FOUND
    ),
    BAD_PROPERTY(
        ApiErrorCodeValidator.validateCode(100004),
        HttpStatus.BAD_REQUEST
    ),
    PARAMETER_TYPE_MISMATCH(
        ApiErrorCodeValidator.validateCode(100006),
        HttpStatus.BAD_REQUEST
    )
    ;

    companion object {
        private const val TITLE = "TITLE"
        private const val MESSAGE = "MESSAGE"
    }

    override fun getCode(): Int {
        return code
    }

    override fun getTitleKey(): String {
        return toPropertyKey(TITLE)
    }

    override fun getMessageKey(): String {
        return toPropertyKey(MESSAGE)
    }

    override fun getHttpStatus(): HttpStatus {
        return status
    }

    private fun toPropertyKey(key: String): String {
        return "${code}.${name}.${key}"
    }

}
