package io.gijung.aichatbot.auth.application.port.`in`

import io.gijung.aichatbot.auth.domain.model.vo.UserId

fun interface SignUpUseCase {
    fun signUp(command: SignUpCommand): UserId
}