package io.gijung.aichatbot.auth.infrastructure.persistence

import io.gijung.aichatbot.auth.application.port.out.UserAccountRepository
import io.gijung.aichatbot.auth.domain.model.UserAccount
import io.gijung.aichatbot.auth.domain.model.vo.Email
import io.gijung.aichatbot.auth.domain.model.vo.Password
import io.gijung.aichatbot.user.domain.model.vo.UserId
import io.gijung.aichatbot.global.config.JpaConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig::class, UserAccountRepositoryImpl::class, UserAccountMapper::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserAccountRepositoryImplTest {

    companion object {
        @JvmStatic
        val USER_NAME = "username"
        @JvmStatic
        val PASSWORD = "password"
        @JvmStatic
        val DATABASE_NAME = "testdb"

        @Container
        @JvmStatic
        val postgresContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:16-alpine")
            .withDatabaseName(DATABASE_NAME)
            .withUsername(USER_NAME)
            .withPassword(PASSWORD)
    }

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
        // given
        userAccountRepository.save(userAccount)

        // when
        val exists = userAccountRepository.existsByEmail(userAccount.email)

        // then
        assertThat(exists).isTrue()
    }

    @Test
    fun `존재하지 않는 이메일로 확인 시 false를 반환한다`() {
        // when
        val exists = userAccountRepository.existsByEmail(Email("non-existing@test.com"))

        // then
        assertThat(exists).isFalse()
    }

    @Test
    fun `이메일로 조회 시 결과가 없으면 null을 반환한다`() {
        // when
        val foundUserAccount = userAccountRepository.findByEmail(Email("non-existing@test.com"))

        // then
        assertThat(foundUserAccount).isNull()
    }
}
