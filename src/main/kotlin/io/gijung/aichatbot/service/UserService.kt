package io.gijung.aichatbot.service

import io.gijung.aichatbot.auth.JwtGenerateDTO
import io.gijung.aichatbot.auth.JwtGenerator
import io.gijung.aichatbot.controller.request.LoginRequest
import io.gijung.aichatbot.domain.user.User
import io.gijung.aichatbot.controller.request.SignUpRequest
import io.gijung.aichatbot.controller.response.LoginResponse
import io.gijung.aichatbot.controller.response.SignUpResponse
import io.gijung.aichatbot.exception.error.CustomUserError
import io.gijung.aichatbot.exception.exception.CustomUserException
import io.gijung.aichatbot.repository.UserAccountRepository
import io.gijung.aichatbot.repository.UserProfileRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userAccountRepository: UserAccountRepository,
    private val userProfileRepository: UserProfileRepository,
    private val bCryptPasswordEncoder: PasswordEncoder,
    private val jwtGenerator: JwtGenerator
) {

    @Transactional
    fun signUp(request: SignUpRequest): SignUpResponse {
        if (isEmailExists(request.email)) {
            throw CustomUserException(CustomUserError.DUPLICATED_EMAIL, "이미 존재하는 이메일입니다.")
        }

        val user = User.create(request.email, request.password, request.name, bCryptPasswordEncoder)
        userAccountRepository.save(user.userAccountEntity)
        userProfileRepository.save(user.userProfileEntity)

        return SignUpResponse(
            id = user.id,
            email = user.email,
            nickname = user.name
        )
    }

    fun login(request: LoginRequest): LoginResponse {
        val userAccount = userAccountRepository.findByEmail(request.email)
            ?: throw CustomUserException(CustomUserError.INVALID_CREDENTIALS, "존재하지 않는 이메일입니다.")

        if (!bCryptPasswordEncoder.matches(request.password, userAccount.password)) {
            throw CustomUserException(CustomUserError.INVALID_CREDENTIALS, "비밀번호가 일치하지 않습니다.")
        }

        val jwtGenerateDTO = JwtGenerateDTO(userAccount.id, userAccount.role)
        val token = jwtGenerator.generateToken(jwtGenerateDTO)

        return LoginResponse(token)
    }

    private fun isEmailExists(email: String): Boolean {
        return userAccountRepository.existsByEmail(email)
    }
}