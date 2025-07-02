package io.gijung.aichatbot.auth

data class JwtGenerateDTO(
    val userId: String,
    val userRole: String,
)