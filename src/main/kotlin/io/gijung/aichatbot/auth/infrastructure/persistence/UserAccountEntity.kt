package io.gijung.aichatbot.auth.infrastructure.persistence

import io.gijung.aichatbot.repository.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "user_account",
    indexes = [Index(name = "idx_user_account_email", columnList = "email")]
)
class UserAccountEntity(
    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    val id: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val role: String,
) : BaseEntity() {
    protected constructor() : this("", "", "", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserAccountEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}