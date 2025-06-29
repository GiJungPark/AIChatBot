package io.gijung.aichatbot.domain.user

import io.gijung.aichatbot.repository.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user_profile")
data class UserProfileEntity(
    @Id
    val id: String,
    val name: String,
    val role: String
) : BaseEntity() {
    protected constructor() : this("", "", "")
}