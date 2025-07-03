package io.gijung.aichatbot.auth

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class JwtFilter(
    private val jwtValidator: JwtValidator
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val token = extractToken(exchange) ?: return chain.filter(exchange)

        if (!jwtValidator.validateToken(token)) {
            return chain.filter(exchange)
        }

        val claims = jwtValidator.parseClaims(token)
        val userId = claims.subject
        val userRole = claims["role"] as? String ?: "MEMBER"

        val authentication = UsernamePasswordAuthenticationToken(
            userId, null, listOf(SimpleGrantedAuthority(userRole))
        )
        val securityContext = SecurityContextImpl(authentication)

        return chain.filter(exchange)
            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)))
    }

    private fun extractToken(exchange: ServerWebExchange): String? {
        val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        return authHeader?.takeIf { it.startsWith("Bearer ") }?.substring(7)
    }
}