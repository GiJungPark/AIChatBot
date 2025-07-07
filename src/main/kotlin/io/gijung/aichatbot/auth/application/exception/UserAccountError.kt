package io.gijung.aichatbot.auth.application.exception

import io.gijung.aichatbot.exception.error.CustomError
import org.springframework.http.HttpStatus

enum class UserAccountError(
    override val code: String,
    override val description: String,
    override val httpStatus: HttpStatus
) : CustomError {
    DUPLICATE_EMAIL(
        code = "USER_ACCOUNT_001",
        description = "이미 사용 중인 이메일입니다.",
        httpStatus = HttpStatus.BAD_REQUEST
    ),
    INVALID_CREDENTIALS(
        code = "USER_ACCOUNT_002",
        description = "이메일 또는 비밀번호가 올바르지 않습니다." , HttpStatus.UNAUTHORIZED,
    )
}