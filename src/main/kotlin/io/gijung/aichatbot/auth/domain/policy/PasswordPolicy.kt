package io.gijung.aichatbot.auth.domain.policy

fun interface PasswordPolicy {
    fun validate(password: String)
}