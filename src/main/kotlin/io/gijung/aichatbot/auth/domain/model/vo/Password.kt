package io.gijung.aichatbot.auth.domain.model.vo

import com.fasterxml.jackson.annotation.JsonCreator
import io.gijung.aichatbot.auth.domain.exception.PasswordValidationError
import io.gijung.aichatbot.auth.domain.exception.PasswordValidationException

@JvmInline
value class Password private constructor(val value: String) {
    companion object {
        @JsonCreator
        @JvmStatic
        fun from(password: String): Password {
            return Password(password)
        }

        operator fun invoke(password: String): Password = from(password)
    }

    init {
        validatePassword(value)
    }

    private fun validatePassword(password: String) {
        if (password.length < 8) {
            throw PasswordValidationException(PasswordValidationError.LENGTH_TOO_SHORT, "비밀번호는 최소 8자 이상이어야 합니다.")
        }
        if (password.none { it.isUpperCase() }) {
            throw PasswordValidationException(PasswordValidationError.NO_UPPERCASE, "비밀번호는 하나 이상의 대문자를 포함해야 합니다.")
        }
        if (password.none { it.isLowerCase() }) {
            throw PasswordValidationException(PasswordValidationError.NO_LOWERCASE, "비밀번호는 하나 이상의 소문자를 포함해야 합니다.")
        }
        if (password.none { it.isDigit() }) {
            throw PasswordValidationException(PasswordValidationError.NO_DIGIT, "비밀번호는 하나 이상의 숫자를 포함해야 합니다.")
        }
        if (password.none { !it.isLetterOrDigit() }) {
            throw PasswordValidationException(PasswordValidationError.NO_SPECIAL_CHARACTER, "비밀번호는 하나 이상의 특수 문자를 포함해야 합니다.")
        }
    }
}