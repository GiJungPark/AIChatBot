package io.gijung.aichatbot.auth.application.port.`in`

data class LoginCommand (
    val email: String,
    val password: String,
)