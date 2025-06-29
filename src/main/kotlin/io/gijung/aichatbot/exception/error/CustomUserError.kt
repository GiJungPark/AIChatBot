package io.gijung.aichatbot.exception.error

import org.springframework.http.HttpStatus

enum class CustomUserError(
    override val code: String,
    override val description: String,
    override val httpStatus: HttpStatus
) : CustomError {
    DUPLICATED_EMAIL("USER-001", "중복된 이메일이 존재합니다.", HttpStatus.CONFLICT)
}