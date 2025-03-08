package io.gijung.springboot.infrastructure.postgres.chat

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "threads")
data class ThreadEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val userId: Long,

    @OneToMany(mappedBy = "thread", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val chats: MutableList<ChatEntity> = mutableListOf(),

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    constructor() : this(userId = 0L) {

    }
}