package io.gijung.aichatbot.auth.application.port.`in`

data class SignUpCommand(
    val email: String,
    val password: String,
    val name: String
)
