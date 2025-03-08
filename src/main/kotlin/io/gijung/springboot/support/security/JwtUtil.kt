package io.gijung.springboot.support.security

import io.gijung.springboot.domain.user.Role
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil(
    @Value("\${security.jwt.key}") private val secretKeyString: String,
    @Value("\${security.jwt.expire-time}") private val expireTime: Long
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(secretKeyString.toByteArray())

    fun generateToken(userId: String, role: Role): String {
        val now = Date()
        val expiryDate = Date(now.time + expireTime)

        return Jwts.builder()
            .setSubject(userId)
            .claim("role", role.name)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(secretKey)
            .compact()
    }

    fun isTokenValid(token: String): Boolean {
        return try {
            val claims = getClaims(token)
            claims != null && claims.expiration.after(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun getClaims(token: String): Claims? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            throw IllegalArgumentException("유효하지 않은 토큰입니다.")
        }
    }

    fun extractUserId(token: String): Any? {
        return getClaims(token)?.subject
    }
}