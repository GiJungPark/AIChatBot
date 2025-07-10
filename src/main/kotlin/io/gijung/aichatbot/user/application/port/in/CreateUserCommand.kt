package io.gijung.aichatbot.user.application.port.`in`

data class CreateUserCommand(
    val id: String,
    val username: String,
)