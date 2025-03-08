package io.gijung.springboot.infrastructure.postgres.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun existsByEmailAndPassword(email: String, password: String): Boolean
}