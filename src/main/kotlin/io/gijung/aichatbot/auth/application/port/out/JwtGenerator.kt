package io.gijung.aichatbot.auth.application.port.out

import io.gijung.aichatbot.auth.domain.model.UserRole
import io.gijung.aichatbot.auth.domain.model.vo.UserId

interface JwtGenerator {
    fun generateAccessToken(id: UserId, role: UserRole): String
}