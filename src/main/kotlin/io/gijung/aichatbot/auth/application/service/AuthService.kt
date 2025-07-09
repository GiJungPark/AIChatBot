package io.gijung.aichatbot.auth.application.service

import io.gijung.aichatbot.auth.application.exception.DuplicateEmailException
import io.gijung.aichatbot.auth.application.exception.NotFoundEmailException
import io.gijung.aichatbot.auth.application.exception.NotMatchPasswordException
import io.gijung.aichatbot.auth.application.port.`in`.LoginCommand
import io.gijung.aichatbot.auth.application.port.`in`.LoginUseCase
import io.gijung.aichatbot.auth.application.port.`in`.SignUpCommand
import io.gijung.aichatbot.auth.application.port.`in`.SignUpUseCase
import io.gijung.aichatbot.auth.application.port.out.JwtGenerator
import io.gijung.aichatbot.auth.application.port.out.UserIdGenerator
import io.gijung.aichatbot.auth.application.port.out.UserAccountRepository
import io.gijung.aichatbot.auth.domain.model.UserAccount
import io.gijung.aichatbot.auth.domain.model.event.UserSignUpEvent
import io.gijung.aichatbot.auth.domain.model.vo.Email
import io.gijung.aichatbot.auth.domain.model.vo.Password
import io.gijung.aichatbot.user.domain.model.vo.UserId
import io.gijung.aichatbot.auth.domain.policy.PasswordPolicy
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userAccountRepository: UserAccountRepository,
    private val jwtGenerator: JwtGenerator,
    private val userIdGenerator: UserIdGenerator,
    private val passwordEncoder: PasswordEncoder,
    private val passwordPolicy: PasswordPolicy,
    private val eventPublisher: ApplicationEventPublisher,
) : SignUpUseCase, LoginUseCase {
    @Transactional
    override fun signUp(command: SignUpCommand): UserId {
        if (userAccountRepository.existsByEmail(Email(command.email))) {
            throw DuplicateEmailException("Exists Email : ${command.email}")
        }

        val userId = userIdGenerator.generate()
        passwordPolicy.validate(command.password)
        val encodedPassword = passwordEncoder.encode(command.password)

        val userAccount = UserAccount.createMember(
            id = userId,
            email = Email(command.email),
            password = Password(encodedPassword)
        )

        userAccountRepository.save(userAccount)

        eventPublisher.publishEvent(
            UserSignUpEvent(
                id = userId.value,
                username = command.name
            )
        )

        return userId
    }

    override fun login(command: LoginCommand): String {
        val userAccount = userAccountRepository.findByEmail(Email(command.email))
            ?: throw NotFoundEmailException("Not Exists Email : ${command.email}")

        if (!passwordEncoder.matches(command.password, userAccount.password.value)) {
            throw NotMatchPasswordException("Not Match Password, User Id : ${userAccount.id.value}")
        }

        val tokens = jwtGenerator.generateAccessToken(id = userAccount.id, role = userAccount.role)

        return tokens
    }


}