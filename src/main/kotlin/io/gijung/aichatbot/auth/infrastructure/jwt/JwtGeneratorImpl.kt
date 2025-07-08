package io.gijung.aichatbot.auth.infrastructure.jwt

import io.gijung.aichatbot.auth.application.port.out.JwtGenerator
import io.gijung.aichatbot.auth.domain.model.UserRole
import io.gijung.aichatbot.auth.domain.model.vo.UserId
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtGeneratorImpl(
    private val jwtProperties: JwtProperties,
    private val clockHolder: ClockHolder
) : JwtGenerator {
    override fun generateAccessToken(id: UserId, role: UserRole): String {
        val now = Date(clockHolder.now())
        val expiry = Date(now.time + jwtProperties.expirationTime)
        val claims: MutableMap<String, Any> = Jwts.claims()

        claims["role"] = role.name

        return Jwts.builder()
            .setSubject(id.value)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .claim("role", role.name)
            .signWith(jwtProperties.secretKey, SignatureAlgorithm.HS256)
            .compact()
    }
}