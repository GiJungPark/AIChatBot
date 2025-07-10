package io.gijung.aichatbot.auth.application.port.out

import io.gijung.aichatbot.user.domain.model.vo.UserId

interface UserIdGenerator {
    fun generate(): UserId
}