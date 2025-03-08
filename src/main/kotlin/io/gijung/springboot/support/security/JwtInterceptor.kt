package io.gijung.springboot.support.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class JwtInterceptor(private val jwtUtil: JwtUtil) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val path = request.requestURI

        if (path.startsWith("/api/v1/users/signup") || path.startsWith("/api/v1/users/login")) {
            return true
        }

        val token = request.getHeader("Authorization")?.removePrefix("Bearer ")

        if (token.isNullOrBlank() || !jwtUtil.isTokenValid(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing token")
            return false
        }

        val userId = jwtUtil.extractUserId(token)
        request.setAttribute("userId", userId)

        return true
    }
}