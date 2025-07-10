package io.gijung.aichatbot.user.domain.model.vo

import io.gijung.aichatbot.user.domain.exception.UserNameValidationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UserNameTest {

    @ParameterizedTest
    @ValueSource(strings = ["홍길동", "박원", "남궁민수"])
    fun `유효한 한글 이름으로 UserName을 생성한다`(name: String) {
        val userName = UserName(name)

        assertThat(userName.value).isEqualTo(name)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "홍", "Gildong", "홍길동1", "홍길동!"])
    fun `유효하지 않은 이름 형식에 대해 예외를 발생시킨다`(invalidName: String) {
        assertThatThrownBy { UserName(invalidName) }
            .isInstanceOf(UserNameValidationException::class.java)
            .hasMessage("Invalidate User Name: $invalidName")
    }
}