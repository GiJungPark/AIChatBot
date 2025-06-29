package io.gijung.aichatbot.domain.user

import io.gijung.aichatbot.repository.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class UserAccountEntity(
    @Id
    val id: String,
    val email: String,
    val password: String
) : BaseEntity() {
    protected constructor() : this("", "", "")
}