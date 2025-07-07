package io.gijung.aichatbot.auth.domain.exception

import io.gijung.aichatbot.exception.error.CustomError
import org.springframework.http.HttpStatus

enum class PasswordValidationError(
    override val code: String,
    override val description: String,
    override val httpStatus: HttpStatus
) : CustomError {
    LENGTH_TOO_SHORT(
        code = "PASSWORD_001",
        description = "비밀번호는 최소 8자 이상이어야 합니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),

    NO_UPPERCASE(
        code = "PASSWORD_002",
        description = "비밀번호는 하나 이상의 대문자를 포함해야 합니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),

    NO_LOWERCASE(
        code = "PASSWORD_003",
        description = "비밀번호는 하나 이상의 소문자를 포함해야 합니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),

    NO_DIGIT(
        code = "PASSWORD_004",
        description = "비밀번호는 하나 이상의 숫자를 포함해야 합니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),

    NO_SPECIAL_CHARACTER(
        code = "PASSWORD_005",
        description = "비밀번호는 하나 이상의 특수 문자를 포함해야 합니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
}