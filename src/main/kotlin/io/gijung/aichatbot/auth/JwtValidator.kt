package io.gijung.aichatbot.auth

import io.gijung.aichatbot.config.JwtProperties
import io.jsonwebtoken.*
import org.springframework.stereotype.Component

@Component
class JwtValidator(private val jwtProperties: JwtProperties) {

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(jwtProperties.secretKey).build().parseClaimsJws(token)
            true
        } catch (e: SecurityException) {
            false // Invalid JWT signature
        } catch (e: MalformedJwtException) {
            false // Invalid JWT token
        } catch (e: ExpiredJwtException) {
            false // Expired JWT token
        } catch (e: UnsupportedJwtException) {
            false // Unsupported JWT token
        } catch (e: IllegalArgumentException) {
            false // JWT claims string is empty
        }
    }

    fun parseClaims(token: String): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(jwtProperties.secretKey).build().parseClaimsJws(token).body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }
}