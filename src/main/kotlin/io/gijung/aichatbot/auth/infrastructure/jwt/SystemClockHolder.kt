package io.gijung.aichatbot.auth.infrastructure.jwt

import org.springframework.stereotype.Component

@Component
class SystemClockHolder : ClockHolder {
    override fun now(): Long = System.currentTimeMillis()
}