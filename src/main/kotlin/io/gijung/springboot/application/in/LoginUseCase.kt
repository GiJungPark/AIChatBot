package io.gijung.springboot.application.`in`

interface LoginUseCase {
    fun accountValidate(email: String, password: String): Boolean
}