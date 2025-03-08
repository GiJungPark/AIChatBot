package io.gijung.springboot.presentation.api.v1.users.request

data class LoginRequest(
    val email: String,
    val password: String
)
