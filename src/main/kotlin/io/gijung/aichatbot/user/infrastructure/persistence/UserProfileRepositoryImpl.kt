package io.gijung.aichatbot.user.infrastructure.persistence

import io.gijung.aichatbot.user.application.port.out.UserProfileRepository
import io.gijung.aichatbot.user.domain.model.UserProfile
import io.gijung.aichatbot.user.domain.model.vo.UserId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserProfileRepositoryImpl(
    private val userProfileJpaRepository: UserProfileJpaRepository,
    private val userProfileMapper: UserProfileMapper
) : UserProfileRepository {
    override fun existsById(id: UserId): Boolean {
        return userProfileJpaRepository.existsById(id.value)
    }

    override fun findById(id: UserId): UserProfile? {
        return userProfileJpaRepository.findByIdOrNull(id.value)?.let { it -> userProfileMapper.toDomain(it) }
    }

    override fun save(userProfile: UserProfile) {
        val entity = userProfileMapper.toEntity(userProfile)

        userProfileJpaRepository.save(entity)
    }
}