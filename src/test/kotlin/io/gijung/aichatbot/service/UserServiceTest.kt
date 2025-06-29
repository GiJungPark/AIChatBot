package io.gijung.aichatbot.service

import io.gijung.aichatbot.controller.request.SignUpRequest
import io.gijung.aichatbot.exception.exception.CustomUserException
import io.gijung.aichatbot.repository.UserAccountRepository
import io.gijung.aichatbot.repository.UserProfileRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @Mock
    lateinit var userAccountRepository: UserAccountRepository

    @Mock
    lateinit var userProfileRepository: UserProfileRepository

    @InjectMocks
    lateinit var userService: UserService

    @Test
    fun `회원가입 성공`() {
        val request = SignUpRequest("test@example.com", "password", "홍길동")

        `when`(userAccountRepository.existsByEmail(request.email)).thenReturn(false)
        `when`(userAccountRepository.save(any())).thenAnswer { it.getArgument(0) }
        `when`(userProfileRepository.save(any())).thenAnswer { it.getArgument(0) }

        val response = userService.signUp(request)

        assertEquals(request.email, response.email)
        assertEquals(request.name, response.nickname)

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

}