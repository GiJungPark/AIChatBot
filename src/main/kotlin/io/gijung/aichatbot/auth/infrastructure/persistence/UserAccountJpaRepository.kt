package io.gijung.aichatbot.auth.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface UserAccountJpaRepository : JpaRepository<UserAccountEntity, String> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): UserAccountEntity?
}