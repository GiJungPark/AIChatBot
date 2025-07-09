package io.gijung.aichatbot.auth.domain.model

import io.gijung.aichatbot.auth.domain.model.vo.Email
import io.gijung.aichatbot.auth.domain.model.vo.Password
import io.gijung.aichatbot.user.domain.model.vo.UserId

class UserAccount private constructor(
    val id: UserId,
    val email: Email,
    val password: Password,
    val role: UserRole
) {
    companion object {
        fun createMember(id: UserId, email: Email, password: Password): UserAccount {
            return UserAccount(
                id = id,
                email = email,
                password = password,
                role = UserRole.MEMBER
            )
        }

        fun createAdmin(id: UserId, email: Email, password: Password): UserAccount {
            return UserAccount(
                id = id,
                email = email,
                password = password,
                role = UserRole.ADMIN
            )
        }

        fun from(id: UserId, email: Email, password: Password, role: UserRole): UserAccount {
            return UserAccount(
                id = id,
                email = email,
                password = password,
                role = role
            )
        }
    }
}
