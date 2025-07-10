package io.gijung.aichatbot.global.error.handler

import io.gijung.aichatbot.global.error.dto.ErrorResponse
import io.gijung.aichatbot.global.error.code.CustomError
import io.gijung.aichatbot.global.error.code.CustomServerError
import io.gijung.aichatbot.global.error.exception.AbstractCustomException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AbstractCustomException::class)
    fun customExceptionHandler(e: AbstractCustomException): ResponseEntity<ErrorResponse> {
        return errorResponse(e.error)
    }

    @ExceptionHandler(WebExchangeBindException::class)
    fun webExchangeBindExceptionHandler(e: WebExchangeBindException): ResponseEntity<ErrorResponse> {
        val error = CustomServerError.VALIDATION_ERROR
        val message = e.bindingResult.fieldErrors.firstOrNull()?.defaultMessage ?: error.description
        return errorResponse(error, message)
    }

    @ExceptionHandler(RuntimeException::class)
    fun runtimeExceptionHandler(e: RuntimeException): ResponseEntity<ErrorResponse> {
        return errorResponse(CustomServerError.INTERNAL_SERVER_ERROR)
    }

    private fun errorResponse(customError: CustomError): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(customError.httpStatus)
            .body(ErrorResponse.from(customError))
    }

    private fun errorResponse(customError: CustomError, message: String): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(customError.httpStatus)
            .body(ErrorResponse(code = customError.code, message = message))
    }

}