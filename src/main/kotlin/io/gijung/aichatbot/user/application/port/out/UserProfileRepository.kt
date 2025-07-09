package io.gijung.aichatbot.user.application.port.out

import io.gijung.aichatbot.user.domain.model.UserProfile
import io.gijung.aichatbot.user.domain.model.vo.UserId

interface UserProfileRepository {
    fun existsById(id: UserId): Boolean
    fun findById(id: UserId): UserProfile?
    fun save(userProfile: UserProfile)
}