package io.gijung.aichatbot.domain.user

import io.gijung.aichatbot.repository.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "user_account",
    indexes = [Index(name = "idx_user_account_email", columnList = "email")]
)
data class UserAccountEntity(
    @Id
    val id: String,
    val email: String,
    val password: String
) : BaseEntity() {
    protected constructor() : this("", "", "")
}