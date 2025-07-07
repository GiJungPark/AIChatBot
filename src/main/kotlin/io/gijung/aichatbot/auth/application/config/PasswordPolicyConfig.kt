package io.gijung.aichatbot.auth.application.config

import io.gijung.aichatbot.auth.domain.policy.DefaultPasswordPolicy
import io.gijung.aichatbot.auth.domain.policy.PasswordPolicy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PasswordPolicyConfig {
    @Bean
    fun passwordPolicy(): PasswordPolicy = DefaultPasswordPolicy()
}