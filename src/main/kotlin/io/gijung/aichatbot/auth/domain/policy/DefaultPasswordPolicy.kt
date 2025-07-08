package io.gijung.aichatbot.auth.domain.policy

import io.gijung.aichatbot.auth.domain.exception.PasswordValidationError
import io.gijung.aichatbot.auth.domain.exception.PasswordValidationException

class DefaultPasswordPolicy : PasswordPolicy {

    private val rules = listOf(
        Rule(
            predicate = { it.length >= 8 },
            error = PasswordValidationError.LENGTH_TOO_SHORT,
            message = "비밀번호는 최소 8자 이상이어야 합니다."
        ),
        Rule(
            predicate = { it.any { char -> char.isUpperCase() } },
            error = PasswordValidationError.NO_UPPERCASE,
            message = "비밀번호는 하나 이상의 대문자를 포함해야 합니다."
        ),
        Rule(
            predicate = { it.any { char -> char.isLowerCase() } },
            error = PasswordValidationError.NO_LOWERCASE,
            message = "비밀번호는 하나 이상의 소문자를 포함해야 합니다."
        ),
        Rule(
            predicate = { it.any { char -> char.isDigit() } },
            error = PasswordValidationError.NO_DIGIT,
            message = "비밀번호는 하나 이상의 숫자를 포함해야 합니다."
        ),
        Rule(
            predicate = { it.any { char -> !char.isLetterOrDigit() } },
            error = PasswordValidationError.NO_SPECIAL_CHARACTER,
            message = "비밀번호는 하나 이상의 특수 문자를 포함해야 합니다."
        )
    )

    override fun validate(password: String) {
        rules.forEach { rule ->
            if (!rule.predicate(password)) {
                throw PasswordValidationException(rule.error, rule.message)
            }
        }
    }

    private data class Rule(
        val predicate: (String) -> Boolean,
        val error: PasswordValidationError,
        val message: String
    )
}