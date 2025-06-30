package io.gijung.aichatbot.domain.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

data class User(
    val userAccountEntity: UserAccountEntity,
    val userProfileEntity: UserProfileEntity
) {
    val id: String get() = userAccountEntity.id
    val email: String get() = userAccountEntity.email
    val name: String get() = userProfileEntity.name
    val role: String get() = userProfileEntity.role
    
    companion object {
        fun create(email: String, password: String, name: String, encoder: BCryptPasswordEncoder): User {
            val userId = UUID.randomUUID().toString()
            val userAccountEntity = UserAccountEntity(userId, email, encoder.encode(password))
            val userProfileEntity = UserProfileEntity(userId, name, UserRole.MEMBER.name)
            return User(userAccountEntity, userProfileEntity)
        }
        
        fun createAdmin(email: String, password: String, name: String): User {
            val userId = UUID.randomUUID().toString()
            val userAccountEntity = UserAccountEntity(userId, email, password)
            val userProfileEntity = UserProfileEntity(userId, name, UserRole.ADMIN.name)
            return User(userAccountEntity, userProfileEntity)
        }
    }
}
