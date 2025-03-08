package io.gijung.springboot.infrastructure.postgres.chat

import org.springframework.data.jpa.repository.JpaRepository

interface ChatJpaRepository: JpaRepository<ChatEntity, Long> {
}