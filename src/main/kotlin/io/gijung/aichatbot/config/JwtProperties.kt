package io.gijung.aichatbot.config

import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import javax.crypto.SecretKey

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    private val secret_key: String,
    val expirationTime: Long,
) {
    val secretKey: SecretKey = Keys.hmacShaKeyFor(secret_key.toByteArray())
}
