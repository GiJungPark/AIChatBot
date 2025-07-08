package io.gijung.aichatbot.auth.domain.model

import io.gijung.aichatbot.auth.domain.model.vo.Email
import io.gijung.aichatbot.auth.domain.model.vo.Password
import io.gijung.aichatbot.user.domain.modle.vo.UserId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserAccountTest {

    @Test
    fun `createMember는 MEMBER 역할의 UserAccount를 생성한다`() {
        val userId = UserId("test-user")
        val email = Email("member@test.com")
        val password = Password("Password123!")

        val userAccount = UserAccount.createMember(userId, email, password)

        assertThat(userAccount.id).isEqualTo(userId)
        assertThat(userAccount.email).isEqualTo(email)
        assertThat(userAccount.password).isEqualTo(password)
        assertThat(userAccount.role).isEqualTo(UserRole.MEMBER)
    }

    @Test
    fun `createAdmin은 ADMIN 역할의 UserAccount를 생성한다`() {
        val userId = UserId("test-admin")
        val email = Email("admin@test.com")
        val password = Password("Password123!")

        val userAccount = UserAccount.createAdmin(userId, email, password)

        assertThat(userAccount.id).isEqualTo(userId)
        assertThat(userAccount.email).isEqualTo(email)
        assertThat(userAccount.password).isEqualTo(password)
        assertThat(userAccount.role).isEqualTo(UserRole.ADMIN)
    }

    @Test
    fun `from은 주어진 속성으로 UserAccount를 생성한다`() {
        val userId = UserId("any-user")
        val email = Email("any@test.com")
        val password = Password("Password123!")
        val role = UserRole.MEMBER

        val userAccount = UserAccount.from(userId, email, password, role)

        assertThat(userAccount.id).isEqualTo(userId)
        assertThat(userAccount.email).isEqualTo(email)
        assertThat(userAccount.password).isEqualTo(password)
        assertThat(userAccount.role).isEqualTo(role)
    }
}
