package io.gijung.springboot.infrastructure.postgres.chat

import io.gijung.springboot.domain.chat.Chat
import org.springframework.stereotype.Repository

@Repository
class ChatRepository (
    private val jpaRepository: ChatJpaRepository
){
    fun save(chatEntity: ChatEntity): Chat {
        return jpaRepository.save(chatEntity).toDomain()
    }
}