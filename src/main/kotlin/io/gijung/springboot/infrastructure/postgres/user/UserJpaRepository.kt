package io.gijung.springboot.infrastructure.postgres.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun existsByEmailAndPassword(email: String, password: String): Boolean

    @Query("SELECT u.id FROM UserEntity u WHERE u.email = :email AND u.password = :password")
    fun findUserIdByEmailAndPassword(@Param("email") email: String, @Param("password") password: String): Long?
}