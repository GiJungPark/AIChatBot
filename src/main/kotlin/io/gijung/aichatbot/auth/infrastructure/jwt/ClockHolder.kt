package io.gijung.aichatbot.auth.infrastructure.jwt

fun interface ClockHolder {
    fun now(): Long
}