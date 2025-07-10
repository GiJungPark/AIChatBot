package io.gijung.aichatbot.user.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface UserProfileJpaRepository : JpaRepository<UserProfileEntity, String> {
}