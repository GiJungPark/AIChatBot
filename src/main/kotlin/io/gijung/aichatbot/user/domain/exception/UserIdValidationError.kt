package io.gijung.aichatbot.user.domain.exception

import io.gijung.aichatbot.global.error.code.CustomError
import org.springframework.http.HttpStatus

enum class UserIdValidationError(
    override val code: String,
    override val description: String,
    override val httpStatus: HttpStatus
) : CustomError {
    EMPTY(
        code = "USER_ID_001",
        description = "User Id는 비어있을 수 없습니다.",
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    ),
}