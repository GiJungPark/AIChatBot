package io.gijung.aichatbot.user.domain.model

import io.gijung.aichatbot.user.domain.model.vo.UserId
import io.gijung.aichatbot.user.domain.model.vo.UserName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserProfileTest {

    @Test
    fun `UserId와 UserName으로 UserProfile을 생성한다`() {
        val userId = UserId("test-uuid")
        val username = UserName("홍길동")

        val userProfile = UserProfile.from(userId, username)

        assertThat(userProfile.id).isEqualTo(userId)
        assertThat(userProfile.username).isEqualTo(username)
    }
}