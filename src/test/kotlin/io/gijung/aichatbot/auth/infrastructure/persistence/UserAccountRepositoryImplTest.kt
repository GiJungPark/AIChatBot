package io.gijung.aichatbot.auth.infrastructure.persistence

import io.gijung.aichatbot.auth.application.port.out.UserAccountRepository
import io.gijung.aichatbot.auth.domain.model.UserAccount
import io.gijung.aichatbot.auth.domain.model.vo.Email
import io.gijung.aichatbot.auth.domain.model.vo.Password
import io.gijung.aichatbot.user.domain.model.vo.UserId
import io.gijung.aichatbot.global.config.JpaConfig
import io.gijung.aichatbot.support.PersistenceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(JpaConfig::class, UserAccountRepositoryImpl::class, UserAccountMapper::class)
class UserAccountRepositoryImplTest : PersistenceIntegrationTest() {

    @Autowired
    private lateinit var userAccountRepository: UserAccountRepository

    @Autowired
    private lateinit var userAccountJpaRepository: UserAccountJpaRepository

    private lateinit var userAccount: UserAccount

    @BeforeEach
    fun setUp() {
        userAccount = UserAccount.createMember(
            id = UserId("test-uuid "),
            email = Email("test@test.com"),
            password = Password("Password123!")
        )
    }

    @Test
    fun `UserAccount를 저장하고 이메일로 조회한다`() {
        userAccountRepository.save(userAccount)
        val foundUserAccount = userAccountRepository.findByEmail(userAccount.email)

        assertThat(foundUserAccount).isNotNull
        assertThat(foundUserAccount?.id).isEqualTo(userAccount.id)
        assertThat(foundUserAccount?.email).isEqualTo(userAccount.email)
        assertThat(foundUserAccount?.role).isEqualTo(userAccount.role)
    }

    @Test
    fun `존재하는 이메일로 확인 시 true를 반환한다`() {
        userAccountRepository.save(userAccount)

        val exists = userAccountRepository.existsByEmail(userAccount.email)

        assertThat(exists).isTrue()
    }

    @Test
    fun `존재하지 않는 이메일로 확인 시 false를 반환한다`() {
        val exists = userAccountRepository.existsByEmail(Email("non-existing@test.com"))

        assertThat(exists).isFalse()
    }

    @Test
    fun `이메일로 조회 시 결과가 없으면 null을 반환한다`() {
        val foundUserAccount = userAccountRepository.findByEmail(Email("non-existing@test.com"))

        assertThat(foundUserAccount).isNull()
    }
}
