package io.gijung.aichatbot.user.infrastructure.persistence

import io.gijung.aichatbot.global.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user_profile")
class UserProfileEntity(
    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    val id: String,

    @Column(nullable = false)
    val name: String,
) : BaseEntity() {
    protected constructor() : this("", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserProfileEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}