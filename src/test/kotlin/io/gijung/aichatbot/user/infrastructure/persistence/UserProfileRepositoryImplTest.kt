package io.gijung.aichatbot.user.infrastructure.persistence

import io.gijung.aichatbot.global.config.JpaConfig
import io.gijung.aichatbot.support.PersistenceIntegrationTest
import io.gijung.aichatbot.user.domain.model.UserProfile
import io.gijung.aichatbot.user.domain.model.vo.UserId
import io.gijung.aichatbot.user.domain.model.vo.UserName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(JpaConfig::class, UserProfileRepositoryImpl::class, UserProfileMapper::class)
class UserProfileRepositoryImplTest : PersistenceIntegrationTest() {

    @Autowired
    private lateinit var userProfileRepository: UserProfileRepositoryImpl

    @Autowired
    private lateinit var userProfileJpaRepository: UserProfileJpaRepository

    private lateinit var userProfile: UserProfile

    @BeforeEach
    fun setUp() {
        userProfile = UserProfile.from(
            id = UserId("test-uuid"),
            username = UserName("홍길동")
        )
    }

    @Test
    fun `UserProfile을 저장하고 ID로 조회한다`() {
        userProfileRepository.save(userProfile)

        val exists = userProfileRepository.findById(userProfile.id)

        assertThat(exists).isNotNull
        assertThat(exists?.username).isEqualTo(userProfile.username)
    }

    @Test
    fun `존재하는 ID로 확인 시 true를 반환한다`() {
        userProfileRepository.save(userProfile)

        val exists = userProfileRepository.existsById(userProfile.id)

        assertThat(exists).isTrue()
    }

    @Test
    fun `존재하지 않는 ID로 확인 시 false를 반환한다`() {
        val exists = userProfileRepository.existsById(userProfile.id)

        assertThat(exists).isFalse()
    }

}