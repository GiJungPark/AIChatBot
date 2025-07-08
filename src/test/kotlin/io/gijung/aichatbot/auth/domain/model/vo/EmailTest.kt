package io.gijung.aichatbot.auth.domain.model.vo

import io.gijung.aichatbot.auth.domain.exception.EmailValidationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class EmailTest {

    @Test
    fun `유효한 이메일로 Email 객체를 생성한다`() {
        val value = "test@test.com"

        val email = Email(value)

        assertThat(email.value).isEqualTo(value)
    }

    @ParameterizedTest
    @ValueSource(strings = ["test", "test@", "test@test.", "@test.com"])
    fun `유효하지 않은 이메일로 Email 객체를 생성하면 예외가 발생한다`(emailValue: String) {
        assertThatThrownBy { Email(emailValue) }
            .isInstanceOf(EmailValidationException::class.java)
            .hasMessage("이메일 형식이 올바르지 않습니다.")
    }
}
