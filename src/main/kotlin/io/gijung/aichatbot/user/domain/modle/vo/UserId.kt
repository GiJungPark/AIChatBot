package io.gijung.aichatbot.user.domain.modle.vo

import com.fasterxml.jackson.annotation.JsonCreator
import io.gijung.aichatbot.user.domain.exception.UserIdValidationError
import io.gijung.aichatbot.user.domain.exception.UserIdValidationException

@JvmInline
value class UserId private constructor(val value: String) {
    companion object {
        @JsonCreator
        @JvmStatic
        fun from(id: String): UserId {
            return UserId(id)
        }

        operator fun invoke(id: String): UserId = from(id)
    }

    init {
        validateUserId(value)
    }

    private fun validateUserId(id: String) {
        if (id.isBlank()) {
            throw UserIdValidationException(UserIdValidationError.EMPTY, "User Id는 비어있을 수 없습니다.")
        }
    }

}