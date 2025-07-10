package io.gijung.aichatbot.auth.infrastructure.persistence

import io.gijung.aichatbot.auth.application.port.out.UserAccountRepository
import io.gijung.aichatbot.auth.domain.model.UserAccount
import io.gijung.aichatbot.auth.domain.model.vo.Email
import io.gijung.aichatbot.user.domain.model.vo.UserId
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class UserAccountRepositoryImpl(
    private val userAccountJpaRepository: UserAccountJpaRepository,
    private val mapper: UserAccountMapper,
) : UserAccountRepository {
    @Transactional
    override fun save(userAccount: UserAccount): UserId {
        val entity = mapper.toEntity(userAccount)

        val savedEntity = userAccountJpaRepository.save(entity)

        return mapper.toUserId(savedEntity)
    }

    override fun existsByEmail(email: Email): Boolean {
        return userAccountJpaRepository.existsByEmail(email.value)
    }

    override fun findByEmail(email: Email): UserAccount? {
        return userAccountJpaRepository.findByEmail(email.value)?.let { entity -> mapper.toDomain(entity) }
    }

}