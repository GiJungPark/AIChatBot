package io.gijung.aichatbot.auth.application.service

import io.gijung.aichatbot.auth.application.exception.DuplicateEmailException
import io.gijung.aichatbot.auth.application.exception.NotFoundEmailException
import io.gijung.aichatbot.auth.application.exception.NotMatchPasswordException
import io.gijung.aichatbot.auth.application.port.`in`.LoginCommand
import io.gijung.aichatbot.auth.application.port.`in`.SignUpCommand
import io.gijung.aichatbot.auth.application.port.out.JwtGenerator
import io.gijung.aichatbot.auth.application.port.out.UserAccountRepository
import io.gijung.aichatbot.auth.application.port.out.UserIdGenerator
import io.gijung.aichatbot.auth.domain.model.UserAccount
import io.gijung.aichatbot.auth.domain.model.vo.Email
import io.gijung.aichatbot.auth.domain.model.vo.Password
import io.gijung.aichatbot.user.domain.model.vo.UserId
import io.gijung.aichatbot.auth.domain.policy.PasswordPolicy
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.security.crypto.password.PasswordEncoder

@ExtendWith(MockitoExtension::class)
class AuthServiceTest {

    @InjectMocks
    private lateinit var authService: AuthService

    @Mock
    private lateinit var userAccountRepository: UserAccountRepository

    @Mock
    private lateinit var jwtGenerator: JwtGenerator

    @Mock
    private lateinit var userIdGenerator: UserIdGenerator

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @Mock
    private lateinit var passwordPolicy: PasswordPolicy

    private lateinit var signUpCommand: SignUpCommand
    private lateinit var loginCommand: LoginCommand
    private lateinit var userAccount: UserAccount

    @BeforeEach
    fun setUp() {
        signUpCommand = SignUpCommand(
            email = "test@test.com",
            password = "Password123!",
            name = "TestUser"
        )

        loginCommand = LoginCommand(
            email = "test@test.com",
            password = "Password123!"
        )

        userAccount = UserAccount.createMember(
            id = UserId("test-uuid"),
            email = Email("test@test.com"),
            password = Password("encodedPassword")
        )
    }

    @Test
    fun `회원가입 성공`() {
        `when`(userAccountRepository.existsByEmail(Email(signUpCommand.email))).thenReturn(false)
        `when`(userIdGenerator.generate()).thenReturn(UserId("test-uuid"));
        `when`(passwordEncoder.encode(signUpCommand.password)).thenReturn("hashPassword")
        `when`(userAccountRepository.save(any())).thenReturn(UserId("test-uuid"))

        val response = authService.signUp(signUpCommand)

        assertThat(UserId("test-uuid")).isEqualTo(response)

        verify(userAccountRepository, times(1)).existsByEmail(Email(signUpCommand.email))
        verify(userAccountRepository, times(1)).save(any())
    }

    @Test
    fun `이메일 중복 시 예외 발생`() {
        `when`(userAccountRepository.existsByEmail(Email(signUpCommand.email))).thenReturn(true)

        assertThatThrownBy { authService.signUp(signUpCommand) }
            .isInstanceOf(DuplicateEmailException::class.java)
            .hasMessage("Exists Email : ${signUpCommand.email}")

        verify(userAccountRepository, times(1)).existsByEmail(Email(signUpCommand.email))
        verify(userAccountRepository, never()).save(any())
    }

    @Test
    fun `로그인에 성공하여 JWT 발급`() {
        val expectedToken = "token"

        `when`(userAccountRepository.findByEmail(Email(loginCommand.email))).thenReturn(userAccount)
        `when`(passwordEncoder.matches(loginCommand.password, userAccount.password.value)).thenReturn(true)
        `when`(jwtGenerator.generateAccessToken(userAccount.id, userAccount.role)).thenReturn(expectedToken)

        val response = authService.login(loginCommand)

        assertThat(response).isNotNull()
        assertThat(expectedToken).isEqualTo(response)

        verify(userAccountRepository, times(1)).findByEmail(Email(loginCommand.email))
        verify(passwordEncoder, times(1)).matches(loginCommand.password, userAccount.password.value)
        verify(jwtGenerator, times(1)).generateAccessToken(userAccount.id, userAccount.role)
    }

    @Test
    fun `존재하지 않은 이메일로 로그인 시도시 실패`() {
        `when`(userAccountRepository.findByEmail(Email(loginCommand.email))).thenReturn(null)

        assertThatThrownBy { authService.login(loginCommand) }
            .isInstanceOf(NotFoundEmailException::class.java)
            .hasMessage("Not Exists Email : ${loginCommand.email}")

        verify(userAccountRepository, times(1)).findByEmail(Email(loginCommand.email))
        verify(passwordEncoder, never()).matches(any(), any())
        verify(jwtGenerator, never()).generateAccessToken(MockitoHelper.anyUserId(), any())
    }

    @Test
    fun `잘못된 비밀번호로 로그인 시도시 실패`() {
        `when`(userAccountRepository.findByEmail(Email(loginCommand.email))).thenReturn(userAccount)
        `when`(passwordEncoder.matches(loginCommand.password, userAccount.password.value)).thenReturn(false)

        assertThatThrownBy { authService.login(loginCommand) }
            .isInstanceOf(NotMatchPasswordException::class.java)
            .hasMessage("Not Match Password, User Id : ${userAccount.id.value}")

        verify(userAccountRepository, times(1)).findByEmail(Email(loginCommand.email))
        verify(passwordEncoder, times(1)).matches(loginCommand.password, userAccount.password.value)
        verify(jwtGenerator, never()).generateAccessToken(MockitoHelper.anyUserId(), any())
    }
}
