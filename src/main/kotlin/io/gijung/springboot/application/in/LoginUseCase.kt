package io.gijung.springboot.application.`in`

interface LoginUseCase {
    fun login(email: String, password: String): Long?
}