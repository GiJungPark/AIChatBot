package io.gijung.aichatbot.user.domain.model.vo

import com.fasterxml.jackson.annotation.JsonCreator
import io.gijung.aichatbot.user.domain.exception.UserNameValidationError
import io.gijung.aichatbot.user.domain.exception.UserNameValidationException

@JvmInline
value class UserName private constructor(val value: String) {
    companion object {
        private val userNameRegex = "^[가-힣]{2,5}$".toRegex()

        @JsonCreator
        @JvmStatic
        fun from(username: String): UserName {
            return UserName(username)
        }

        operator fun invoke(username: String): UserName = from(username)
    }

    init {
        validateUserName(value)
    }

    private fun validateUserName(username: String) {
        if (!username.matches(userNameRegex)) {
            throw UserNameValidationException(UserNameValidationError.INVALID_FORMAT, "Invalidate User Name: $username")
        }
    }

}