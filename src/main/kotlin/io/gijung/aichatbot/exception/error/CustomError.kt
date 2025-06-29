package io.gijung.aichatbot.exception.error

import org.springframework.http.HttpStatus

interface CustomError {
    val code: String
    val description: String
    val httpStatus: HttpStatus
}