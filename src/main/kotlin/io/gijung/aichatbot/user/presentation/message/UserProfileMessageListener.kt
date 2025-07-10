package io.gijung.aichatbot.user.presentation.message

import io.gijung.aichatbot.auth.domain.model.event.UserSignUpEvent
import io.gijung.aichatbot.user.application.port.`in`.CreateUserCommand
import io.gijung.aichatbot.user.application.port.`in`.CreateUserProfileUseCase
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class UserProfileMessageListener(
    private val createUserProfileUseCase: CreateUserProfileUseCase,
) {
    @TransactionalEventListener
    fun handleUserSignedUp(event: UserSignUpEvent) {
        val command = CreateUserCommand(
            id = event.id,
            username = event.username,
        )

        createUserProfileUseCase.createUserProfile(command)
    }
}