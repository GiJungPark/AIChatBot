package io.gijung.springboot.application.`in`

interface SignUpUseCase {
    fun signUp(email: String, password: String, name: String)
}