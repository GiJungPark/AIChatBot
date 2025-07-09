package io.gijung.aichatbot.user.domain.exception

import io.gijung.aichatbot.global.error.code.CustomError
import org.springframework.http.HttpStatus

enum class UserNameValidationError (
    override val code: String,
    override val description: String,
    override val httpStatus: HttpStatus
) : CustomError {
    INVALID_FORMAT(
        code = "USER_NAME_001",
        description = "이름은 2~5자의 한글만 입력 가능합니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
}