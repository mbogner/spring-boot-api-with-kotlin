package com.openresearch.springapi.error

import com.openresearch.springapi.model.ErrorArgumentDto
import com.openresearch.springapi.model.ErrorDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.data.mapping.PropertyReferenceException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Component
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import java.time.Instant
import java.util.Locale
import javax.validation.ConstraintViolationException

@Component
@ControllerAdvice
class ApiExceptionHandler @Autowired constructor(
    private val messageSource: MessageSource,
) {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(
        HttpMessageNotReadableException::class,
        HttpRequestMethodNotSupportedException::class,
        HttpMediaTypeNotSupportedException::class,
        MissingServletRequestParameterException::class,
        ConstraintViolationException::class,
        MethodArgumentNotValidException::class,
    )
    fun handleBadRequest(
        exception: Exception,
        locale: Locale
    ): ResponseEntity<ErrorDto> {
        log.warn(
            "handleBadRequest(locale={}, exceptionClass={}, message={})",
            locale,
            exception.javaClass.name,
            exception.message
        )
        return createResponseEntity(
            createErrorDto(messageSource, locale, ApiErrorCode.BAD_REQUEST, emptyMap()),
            ApiErrorCode.BAD_REQUEST.getHttpStatus()
        )
    }

    @ExceptionHandler
    fun handleMissingRequestHandler(
        exception: MissingRequestHeaderException,
        locale: Locale
    ): ResponseEntity<ErrorDto> {
        log.warn(
            "handleMissingRequestHandler(locale={}, exceptionClass={}, message={})",
            locale,
            exception.javaClass.name,
            exception.message
        )

        if (exception.headerName.equals("X-Tenant-Key", ignoreCase = true)) return createResponseEntity(
            createErrorDto(
                messageSource,
                locale,
                ApiErrorCode.MISSING_TENANT_HEADER,
                mapOf("header" to exception.headerName)
            ),
            ApiErrorCode.MISSING_TENANT_HEADER.getHttpStatus()
        )

        return createResponseEntity(
            createErrorDto(messageSource, locale, ApiErrorCode.BAD_REQUEST, mapOf("header" to exception.headerName)),
            ApiErrorCode.BAD_REQUEST.getHttpStatus()
        )
    }

    @ExceptionHandler
    fun handleNoHandlerFoundException(
        exception: NoHandlerFoundException,
        locale: Locale
    ): ResponseEntity<ErrorDto> {
        log.debug(
            "handleNoHandlerFoundException(locale={}, exceptionClass={}, message={})",
            locale,
            exception.javaClass.name,
            exception.message
        )
        val method = exception.httpMethod
        val requestURL = exception.requestURL
        return createResponseEntity(
            createErrorDto(
                messageSource, locale, ApiErrorCode.NOT_FOUND, mapOf(
                    "method" to method,
                    "url" to requestURL,
                )
            ),
            ApiErrorCode.NOT_FOUND.getHttpStatus()
        )
    }

    @ExceptionHandler
    fun handleErrorCodeException(
        exception: ErrorCodeException,
        locale: Locale
    ): ResponseEntity<ErrorDto> {
        log.warn(
            "handleErrorCodeException(locale={}, exceptionClass={}, message={})",
            locale,
            exception.javaClass.name,
            exception.message
        )
        //log.debug("exception", exception)
        return createResponseEntity(
            createErrorDto(messageSource, locale, exception.errorCode, exception.args),
            exception.errorCode.getHttpStatus()
        )
    }

    @ExceptionHandler
    fun handlePropertyReferenceException(
        exception: PropertyReferenceException,
        locale: Locale
    ): ResponseEntity<ErrorDto> {
        log.warn(
            "handlePropertyReferenceException(locale={}, exceptionClass={}, message={})",
            locale,
            exception.javaClass.name,
            exception.message
        )
        return createResponseEntity(
            createErrorDto(
                messageSource,
                locale,
                ApiErrorCode.BAD_PROPERTY,
                mapOf("property" to exception.propertyName)
            ),
            ApiErrorCode.BAD_PROPERTY.getHttpStatus()
        )
    }

    @ExceptionHandler
    fun handleMethodArgumentTypeMismatchException(
        exception: MethodArgumentTypeMismatchException,
        locale: Locale
    ): ResponseEntity<ErrorDto> {
        log.debug(
            "handlePropertyReferenceException(locale={}, exceptionClass={}, message={})",
            locale,
            exception.javaClass.name,
            exception.message
        )
        val args = mapOf(
            "name" to exception.name,
            "required_type" to exception.requiredType?.name,
            "value" to exception.value,
        )
        return createResponseEntity(
            createErrorDto(messageSource, locale, ApiErrorCode.PARAMETER_TYPE_MISMATCH, args),
            ApiErrorCode.PARAMETER_TYPE_MISMATCH.getHttpStatus()
        )
    }

    @ExceptionHandler
    fun handleFallback(
        exception: Exception,
        locale: Locale
    ): ResponseEntity<ErrorDto> {
        log.error("exception fallback", exception)
        return createResponseEntity(
            createErrorDto(messageSource, locale, ApiErrorCode.GENERIC_ERROR, emptyMap()),
            ApiErrorCode.GENERIC_ERROR.getHttpStatus()
        )
    }

    private fun createResponseEntity(
        errorDTO: ErrorDto,
        httpStatus: HttpStatus
    ): ResponseEntity<ErrorDto> {
        log.debug("createResponseEntity(errorDTO={}, httpStatus={})", errorDTO, httpStatus)
        return ResponseEntity<ErrorDto>(errorDTO, HttpHeaders(), httpStatus)
    }

    private fun createErrorDto(
        messageSource: MessageSource,
        locale: Locale,
        errorCode: ErrorCode,
        args: Map<String, Any?>?
    ): ErrorDto {
        log.debug(
            "createErrorDto(messageSource={}, locale={}, errorCode={}, args={})",
            messageSource,
            locale,
            errorCode,
            args
        )
        val argsArray: Array<Any?> = args?.values?.toTypedArray() ?: arrayOfNulls(0)
        val responseTitle = messageSource.getMessage(errorCode.getTitleKey(), argsArray, locale)
        val responseMessage = messageSource.getMessage(errorCode.getMessageKey(), argsArray, locale)
        val errorArgs: MutableList<ErrorArgumentDto> = ArrayList(argsArray.size)
        if (null != args) {
            for (key in args.keys) {
                val value = args[key]
                val type: String = if (null == value) {
                    "NoType"
                } else {
                    value.javaClass.name
                }
                errorArgs.add(
                    ErrorArgumentDto()
                        .key(key)
                        .value(value.toString())
                        .type(type)
                )
            }
        }
        return ErrorDto()
            .time(Instant.now())
            .code(errorCode.getCode().toString())
            .title(responseTitle)
            .message(responseMessage)
            .args(errorArgs)
    }
}
