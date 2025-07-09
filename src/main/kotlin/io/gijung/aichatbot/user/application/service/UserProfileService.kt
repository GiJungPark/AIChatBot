package io.gijung.aichatbot.user.application.service

import io.gijung.aichatbot.user.application.port.`in`.CreateUserCommand
import io.gijung.aichatbot.user.application.port.`in`.CreateUserProfileUseCase
import io.gijung.aichatbot.user.application.port.out.UserProfileRepository
import io.gijung.aichatbot.user.domain.model.UserProfile
import io.gijung.aichatbot.user.domain.model.vo.UserId
import io.gijung.aichatbot.user.domain.model.vo.UserName
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class UserProfileService(
    private val userProfileRepository: UserProfileRepository
) : CreateUserProfileUseCase {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun createUserProfile(command: CreateUserCommand) {
        val userId = UserId(command.id)

        if (userProfileRepository.existsById(userId)) {
            return
        }

        val userProfile = UserProfile.from(
            id = userId,
            username = UserName(command.username)
        )

        userProfileRepository.save(userProfile)
    }
}