package io.gijung.aichatbot.global.error.code

import org.springframework.http.HttpStatus

interface CustomError {
    val code: String
    val description: String
    val httpStatus: HttpStatus
}