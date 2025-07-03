package io.gijung.aichatbot.repository

import io.gijung.aichatbot.domain.user.UserProfileEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserProfileRepository : JpaRepository<UserProfileEntity, String> {}