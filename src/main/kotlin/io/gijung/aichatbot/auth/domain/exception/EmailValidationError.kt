package io.gijung.aichatbot.auth.domain.exception

import io.gijung.aichatbot.global.error.code.CustomError
import org.springframework.http.HttpStatus

enum class EmailValidationError(
    override val code: String,
    override val description: String,
    override val httpStatus: HttpStatus
) : CustomError {
    INVALID_FORMAT(
        code = "EMAIL_001",
        description = "이메일 형식이 올바르지 않습니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
}