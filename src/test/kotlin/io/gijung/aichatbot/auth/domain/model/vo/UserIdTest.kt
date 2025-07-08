package io.gijung.aichatbot.auth.domain.model.vo

import io.gijung.aichatbot.user.domain.exception.UserIdValidationException
import io.gijung.aichatbot.user.domain.modle.vo.UserId
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class UserIdTest {

    @Test
    fun `유효한 ID로 UserId 객체를 생성한다`() {
        val validId = "some-valid-id"

        val userId = UserId(validId)

        assertThat(userId.value).isEqualTo(validId)
    }

    @Test
    fun `비어있거나 공백인 ID로 UserId 객체를 생성하면 예외가 발생한다`() {
        val blankId = " "

        assertThatThrownBy { UserId(blankId) }
            .isInstanceOf(UserIdValidationException::class.java)
            .hasMessage("User Id는 비어있을 수 없습니다.")
    }
}
