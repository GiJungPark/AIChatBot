package io.gijung.aichatbot.user.domain.model

import io.gijung.aichatbot.user.domain.model.vo.UserId
import io.gijung.aichatbot.user.domain.model.vo.UserName

class UserProfile private constructor(
    val id: UserId,
    val username: UserName
) {
    companion object {
        fun from(id: UserId, username: UserName): UserProfile {
            return UserProfile(
                id = id,
                username = username
            )
        }
    }
}