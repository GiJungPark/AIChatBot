package io.gijung.springboot.presentation.api.v1.users.request

data class SignUpRequest (
    val email: String,
    val password: String,
    val name: String
)