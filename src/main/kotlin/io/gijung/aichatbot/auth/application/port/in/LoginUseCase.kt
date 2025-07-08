package io.gijung.aichatbot.auth.application.port.`in`

fun interface LoginUseCase {
    fun login(command: LoginCommand): String
}