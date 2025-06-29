package io.gijung.aichatbot.repository

import io.gijung.aichatbot.domain.user.UserAccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAccountRepository : JpaRepository<UserAccountEntity, String> {
    fun existsByEmail(email: String): Boolean
}