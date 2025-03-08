package io.gijung.springboot.infrastructure.postgres.chat

import io.gijung.springboot.domain.chat.Chat
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "chats")
data class ChatEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id", nullable = false)
    val thread: ThreadEntity,

    @Column(nullable = false, columnDefinition = "TEXT")
    val question: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val answer: String,

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    constructor() : this(thread = ThreadEntity(), question = "", answer = "", createdAt = LocalDateTime.now()) {

    }

    fun toDomain(): Chat {
        return Chat(thread.userId, question, answer)
    }
}