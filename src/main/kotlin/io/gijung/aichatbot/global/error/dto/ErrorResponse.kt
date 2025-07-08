package io.gijung.aichatbot.global.error.dto

import io.gijung.aichatbot.global.error.code.CustomError

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