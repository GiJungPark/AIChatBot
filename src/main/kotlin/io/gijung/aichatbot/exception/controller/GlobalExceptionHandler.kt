package io.gijung.aichatbot.exception.controller

import io.gijung.aichatbot.exception.error.CustomError
import io.gijung.aichatbot.exception.error.CustomServerError
import io.gijung.aichatbot.exception.exception.AbstractCustomException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AbstractCustomException::class)
    fun customExceptionHandler(e: AbstractCustomException): ResponseEntity<ErrorResponse> {
        return errorResponse(e.error)
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

}