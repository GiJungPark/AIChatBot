package io.gijung.springboot.infrastructure.postgres.user

import io.gijung.springboot.application.out.LoginPort
import io.gijung.springboot.application.out.SignUpPort
import io.gijung.springboot.domain.User
import io.gijung.springboot.domain.UserAccount
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val jpaRepository: UserJpaRepository
) : SignUpPort, LoginPort{
    override fun createUser(user: User) {
        val userEntity = UserEntity.of(user)

        jpaRepository.save(userEntity)
    }

    override fun existsByEmailAndPassword(account: UserAccount): Boolean {
        return jpaRepository.existsByEmailAndPassword(account.email, account.password)
    }
}