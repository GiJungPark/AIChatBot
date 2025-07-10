package io.gijung.aichatbot.user.infrastructure.persistence

import io.gijung.aichatbot.user.domain.model.UserProfile
import io.gijung.aichatbot.user.domain.model.vo.UserId
import io.gijung.aichatbot.user.domain.model.vo.UserName
import org.springframework.stereotype.Component

@Component
class UserProfileMapper {

    fun toEntity(userProfile: UserProfile): UserProfileEntity {
        return UserProfileEntity(
            userProfile.id.value,
            userProfile.username.value
        )
    }

    fun toDomain(userProfileEntity: UserProfileEntity): UserProfile {
        return UserProfile.from(
            UserId(userProfileEntity.id),
            UserName(userProfileEntity.name)
        )
    }

}