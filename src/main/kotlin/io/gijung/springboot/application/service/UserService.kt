package io.gijung.springboot.application.service

import io.gijung.springboot.application.`in`.LoginUseCase
import io.gijung.springboot.application.`in`.SignUpUseCase
import io.gijung.springboot.application.out.LoginPort
import io.gijung.springboot.application.out.SignUpPort
import io.gijung.springboot.domain.user.Role
import io.gijung.springboot.domain.user.User
import io.gijung.springboot.domain.user.UserAccount
import org.springframework.stereotype.Service

@Service
class UserService(
    private val signUpPort: SignUpPort,
    private val loginPort: LoginPort,
) : SignUpUseCase, LoginUseCase {
    override fun signUp(email: String, password: String, name: String) {

        val account = UserAccount(email, password)

        val user = User(userAccount = account, name = name, role = Role.MEMBER)

        signUpPort.createUser(user)
    }

    override fun login(email: String, password: String): Long? {

        val account = UserAccount(email, password)

        return loginPort.findUserIdByEmailAndPassword(account)
    }

}