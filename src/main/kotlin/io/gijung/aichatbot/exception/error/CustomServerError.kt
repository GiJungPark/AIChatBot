package io.gijung.aichatbot.exception.error

import org.springframework.http.HttpStatus

enum class CustomServerError(
    override val code: String,
    override val description: String,
    override val httpStatus: HttpStatus
) : CustomError {
    INTERNAL_SERVER_ERROR(
        code = "SERVER_001",
        description = "알 수 없는 서버 오류가 발생했습니다.",
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    ),
}