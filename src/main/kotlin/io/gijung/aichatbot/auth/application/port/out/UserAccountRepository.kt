package io.gijung.aichatbot.auth.application.port.out

import io.gijung.aichatbot.auth.domain.model.UserAccount
import io.gijung.aichatbot.auth.domain.model.vo.Email
import io.gijung.aichatbot.user.domain.modle.vo.UserId

interface UserAccountRepository {
    fun save(userAccount: UserAccount): UserId
    fun existsByEmail(email: Email): Boolean
    fun findByEmail(email: Email): UserAccount?
}