package io.gijung.aichatbot.exception.controller

import io.gijung.aichatbot.exception.error.CustomError

data class ErrorResponse(
    val code: String,
    val message: String
) {
    companion object {
        fun from(error: CustomError): ErrorResponse {
            return ErrorResponse(code = error.code, message = error.description)
        }
    }
}