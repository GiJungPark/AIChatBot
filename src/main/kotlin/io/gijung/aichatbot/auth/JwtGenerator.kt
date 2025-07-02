package io.gijung.aichatbot.auth

import io.gijung.aichatbot.config.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtGenerator(
    private val jwtProperties: JwtProperties
) {
    fun generateToken(dto: JwtGenerateDTO): String {
        val now = Date()
        val expiry = Date(now.time + jwtProperties.expirationTime)
        val claims: MutableMap<String, Any> = Jwts.claims()

        claims["role"] = dto.userRole

        return Jwts.builder()
            .setSubject(dto.userId)
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(jwtProperties.secretKey, SignatureAlgorithm.HS256)
            .compact()
    }
}