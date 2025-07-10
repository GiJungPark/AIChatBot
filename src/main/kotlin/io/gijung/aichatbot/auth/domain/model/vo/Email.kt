package io.gijung.aichatbot.auth.domain.model.vo

import com.fasterxml.jackson.annotation.JsonCreator
import io.gijung.aichatbot.auth.domain.exception.EmailValidationError
import io.gijung.aichatbot.auth.domain.exception.EmailValidationException

@JvmInline
value class Email private constructor(val value: String) {
    companion object {
        private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()

        @JsonCreator
        @JvmStatic
        fun from(email: String): Email {
            return Email(email)
        }

        operator fun invoke(email: String): Email = from(email)
    }

    init {
        validateEmail(value)
    }

    private fun validateEmail(email: String) {
        if (!email.matches(emailRegex)) {
            throw EmailValidationException(EmailValidationError.INVALID_FORMAT, "이메일 형식이 올바르지 않습니다.")
        }
    }
}