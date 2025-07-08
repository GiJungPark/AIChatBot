package io.gijung.aichatbot.auth.infrastructure.jwt

import io.gijung.aichatbot.auth.domain.model.UserRole
import io.gijung.aichatbot.user.domain.modle.vo.UserId
import io.jsonwebtoken.Jwts
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class JwtGeneratorImplTest {

    private lateinit var jwtGenerator: JwtGeneratorImpl
    private lateinit var jwtProperties: JwtProperties

    private val secret = "this-is-a-very-long-secret-key-for-testing-purpose-only"
    private val expirationTime = 3600000L // 1 hour
    private val nowTime = 1672531200000L // 2023-01-01 00:00:00 UTC

    @BeforeEach
    fun setUp() {
        jwtProperties = JwtProperties(secret, expirationTime)
        jwtGenerator = JwtGeneratorImpl(jwtProperties) { nowTime }
    }

    @Test
    fun `AccessToken을 성공적으로 생성한다`() {
        val userId = UserId("test-user-id")
        val userRole = UserRole.MEMBER

        val token = jwtGenerator.generateAccessToken(userId, userRole)

        assertThat(token).isNotNull
        assertThat(token).isNotEmpty()
    }

    @Test
    fun `생성된 AccessToken에 사용자 ID가 subject로 포함된다`() {
        val userId = UserId("test-user-id")
        val userRole = UserRole.MEMBER
        val token = jwtGenerator.generateAccessToken(userId, userRole)

        val claims = Jwts.parserBuilder()
            .setSigningKey(jwtProperties.secretKey)
            .setClock { Date(nowTime) }
            .build()
            .parseClaimsJws(token)
            .body

        assertThat(claims.subject).isEqualTo(userId.value)
    }

    @Test
    fun `생성된 AccessToken에 role 클레임이 포함된다`() {
        val userId = UserId("test-user-id")
        val userRole = UserRole.MEMBER
        val token = jwtGenerator.generateAccessToken(userId, userRole)

        val claims = Jwts.parserBuilder()
            .setSigningKey(jwtProperties.secretKey)
            .setClock { Date(nowTime) }
            .build()
            .parseClaimsJws(token)
            .body

        assertThat(claims["role"]).isEqualTo(userRole.name)
    }
}
