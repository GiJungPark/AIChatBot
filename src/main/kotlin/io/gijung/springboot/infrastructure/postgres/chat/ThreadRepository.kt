package io.gijung.springboot.infrastructure.postgres.chat

import org.springframework.stereotype.Repository

@Repository
class ThreadRepository (
    private val jpaRepository: ThreadJpaRepository
){
    fun findTopByUserIdOrderByCreatedAtDesc(userId: Long): ThreadEntity? {
        return jpaRepository.findTopByUserIdOrderByCreatedAtDesc(userId)
    }

    fun save(thread: ThreadEntity): ThreadEntity {
        return jpaRepository.save(thread)
    }
}