package io.gijung.springboot.infrastructure.postgres.chat

import org.springframework.data.jpa.repository.JpaRepository

interface ThreadJpaRepository: JpaRepository<ThreadEntity, Long> {
    fun findTopByUserIdOrderByCreatedAtDesc(userId: Long): ThreadEntity?
}