package io.gijung.aichatbot.user.application.service

import io.gijung.aichatbot.user.application.port.`in`.CreateUserCommand
import io.gijung.aichatbot.user.application.port.out.UserProfileRepository
import io.gijung.aichatbot.user.domain.model.vo.UserId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any

@ExtendWith(MockitoExtension::class)
class UserProfileServiceTest {

    @InjectMocks
    private lateinit var userProfileService: UserProfileService

    @Mock
    private lateinit var userProfileRepository: UserProfileRepository

    private lateinit var createUserCommand: CreateUserCommand

    @BeforeEach
    fun setUp() {
        createUserCommand = CreateUserCommand(
            id = "test-uuid",
            username = "홍길동"
        )
    }

    @Test
    fun `새로운 사용자에 대해 UserProfile을 생성한다`() {
        `when`(userProfileRepository.existsById(UserId("test-uuid"))).thenReturn(false)

        userProfileService.createUserProfile(createUserCommand)

        verify(userProfileRepository, times(1)).existsById(UserId("test-uuid"))
        verify(userProfileRepository, times(1)).save(any())
    }

    @Test
    fun `이미 존재하는 사용자에 대해서는 UserProfile을 생성하지 않는다`() {
        `when`(userProfileRepository.existsById(UserId("test-uuid"))).thenReturn(true)

        userProfileService.createUserProfile(createUserCommand)

        verify(userProfileRepository, times(1)).existsById(UserId("test-uuid"))
        verify(userProfileRepository, never()).save(any())
    }
}