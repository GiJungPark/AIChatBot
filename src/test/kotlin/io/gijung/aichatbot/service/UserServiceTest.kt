package io.gijung.aichatbot.service

import io.gijung.aichatbot.auth.JwtGenerateDTO
import io.gijung.aichatbot.auth.JwtGenerator
import io.gijung.aichatbot.controller.request.LoginRequest
import io.gijung.aichatbot.controller.request.SignUpRequest
import io.gijung.aichatbot.domain.user.UserAccountEntity
import io.gijung.aichatbot.domain.user.UserRole
import io.gijung.aichatbot.exception.exception.CustomUserException
import io.gijung.aichatbot.repository.UserAccountRepository
import io.gijung.aichatbot.repository.UserProfileRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @Mock
    lateinit var userAccountRepository: UserAccountRepository

    @Mock
    lateinit var userProfileRepository: UserProfileRepository

    @Mock
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @Mock
    lateinit var jwtGenerator: JwtGenerator

    @InjectMocks
    lateinit var userService: UserService

    @Test
    fun `회원가입 성공`() {
        val request = SignUpRequest("test@example.com", "password", "홍길동")

        `when`(userAccountRepository.existsByEmail(request.email)).thenReturn(false)
        `when`(userAccountRepository.save(any())).thenAnswer { it.getArgument(0) }
        `when`(userProfileRepository.save(any())).thenAnswer { it.getArgument(0) }
        `when`(bCryptPasswordEncoder.encode(request.password)).thenReturn("hashPassword")

        val response = userService.signUp(request)

        assertThat(request.email).isEqualTo(response.email)
        assertThat(request.name).isEqualTo(response.nickname)

        verify(userAccountRepository, times(1)).existsByEmail(request.email)
        verify(userAccountRepository, times(1)).save(any())
        verify(userProfileRepository, times(1)).save(any())
    }

    @Test
    fun `이메일 중복 시 예외 발생`() {
        val request = SignUpRequest("test@example.com", "password", "홍길동")

        `when`(userAccountRepository.existsByEmail(request.email)).thenReturn(true)

        assertThatThrownBy { userService.signUp(request) }
            .isInstanceOf(CustomUserException::class.java)
            .hasMessage("이미 존재하는 이메일입니다.")

        verify(userAccountRepository, times(1)).existsByEmail(request.email)
        verify(userAccountRepository, never()).save(any())
        verify(userProfileRepository, never()).save(any())
    }

    @Test
    fun `로그인에 성공하여 JWT 발급`() {
        val request = LoginRequest("test@example.com", "password123")
        val hashedPassword = "hashedPasswordFromDb" // 데이터베이스에 저장된 암호화된 비밀번호
        val userAccount = UserAccountEntity("user-id-123", "test@example.com", hashedPassword, UserRole.MEMBER.name)
        val expectedToken = "mockedToken"

        `when`(userAccountRepository.findByEmail(request.email)).thenReturn(userAccount)
        `when`(bCryptPasswordEncoder.matches(request.password, hashedPassword)).thenReturn(true)
        `when`(jwtGenerator.generateToken(any())).thenReturn(expectedToken)

        val response = userService.login(request) // userService.login 호출

        assertThat(response).isNotNull()
        assertThat(expectedToken).isEqualTo(response.token)

        verify(userAccountRepository, times(1)).findByEmail(request.email)
        verify(bCryptPasswordEncoder, times(1)).matches(request.password, hashedPassword)
        verify(jwtGenerator, times(1)).generateToken(JwtGenerateDTO(userAccount.id, userAccount.role))
    }

    @Test
    fun `존재하지 않은 이메일로 로그인 시도시 실패`() {
        val request = LoginRequest("nonexistent@example.com", "password123")

        `when`(userAccountRepository.findByEmail(request.email)).thenReturn(null)

        assertThatThrownBy { userService.login(request) }
            .isInstanceOf(CustomUserException::class.java)
            .hasMessage("존재하지 않는 이메일입니다.")

        verify(userAccountRepository, times(1)).findByEmail(request.email)
        verify(bCryptPasswordEncoder, never()).matches(any(), any())
        verify(jwtGenerator, never()).generateToken(any())
    }

    @Test
    fun `잘못된 비밀번호로 로그인 시도시 실패`() {
        val request = LoginRequest("test@example.com", "wrongpassword")
        val hashedPassword = "hashedPasswordFromDb"
        val userAccount = UserAccountEntity("user-id-123", "test@example.com", hashedPassword, "USER")

        `when`(userAccountRepository.findByEmail(request.email)).thenReturn(userAccount)
        `when`(bCryptPasswordEncoder.matches(request.password, hashedPassword)).thenReturn(false)

        assertThatThrownBy { userService.login(request) }
            .isInstanceOf(CustomUserException::class.java)
            .hasMessage("비밀번호가 일치하지 않습니다.")

        verify(userAccountRepository, times(1)).findByEmail(request.email)
        verify(bCryptPasswordEncoder, times(1)).matches(request.password, hashedPassword)
        verify(jwtGenerator, never()).generateToken(any())
    }

}