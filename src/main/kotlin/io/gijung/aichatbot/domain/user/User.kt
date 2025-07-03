package io.gijung.aichatbot.domain.user

import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

data class User(
    val userAccountEntity: UserAccountEntity,
    val userProfileEntity: UserProfileEntity
) {
    val id: String get() = userAccountEntity.id
    val email: String get() = userAccountEntity.email
    val role: String get() = userAccountEntity.role
    val name: String get() = userProfileEntity.name

    companion object {
        fun create(email: String, password: String, name: String, encoder: PasswordEncoder): User {
            val userId = UUID.randomUUID().toString()
            val userAccountEntity = UserAccountEntity(userId, email, encoder.encode(password), UserRole.MEMBER.name)
            val userProfileEntity = UserProfileEntity(userId, name)
            return User(userAccountEntity, userProfileEntity)
        }
        
        fun createAdmin(email: String, password: String, name: String): User {
            val userId = UUID.randomUUID().toString()
            val userAccountEntity = UserAccountEntity(userId, email, password, UserRole.ADMIN.name)
            val userProfileEntity = UserProfileEntity(userId, name)
            return User(userAccountEntity, userProfileEntity)
        }
    }
}
