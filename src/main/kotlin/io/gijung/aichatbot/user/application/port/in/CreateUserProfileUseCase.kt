package io.gijung.aichatbot.user.application.port.`in`

fun interface CreateUserProfileUseCase {
    fun createUserProfile(command: CreateUserCommand)
}