package io.gijung.aichatbot.auth.infrastructure.persistence

import io.gijung.aichatbot.auth.domain.model.UserAccount
import io.gijung.aichatbot.auth.domain.model.UserRole
import io.gijung.aichatbot.auth.domain.model.vo.Email
import io.gijung.aichatbot.auth.domain.model.vo.Password
import io.gijung.aichatbot.user.domain.model.vo.UserId
import org.springframework.stereotype.Component

@Component
class UserAccountMapper {

    fun toEntity(domain: UserAccount): UserAccountEntity {
        return UserAccountEntity(
            id = domain.id.value,
            email = domain.email.value,
            password = domain.password.value,
            role = domain.role.name
        )
    }

    fun toDomain(entity: UserAccountEntity): UserAccount {
        return UserAccount.from(
            id = UserId(entity.id),
            email = Email(entity.email),
            password = Password(entity.password),
            role = UserRole.valueOf(entity.role)
        )
    }

    fun toUserId(userAccountEntity: UserAccountEntity): UserId {
        return UserId(userAccountEntity.id)
    }
}