package io.gijung.aichatbot.auth.domain.model.event

data class UserSignUpEvent(
    val id: String,
    val username: String,
)
