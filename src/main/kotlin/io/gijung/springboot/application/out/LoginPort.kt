package io.gijung.springboot.application.out

import io.gijung.springboot.domain.user.UserAccount

interface LoginPort {
    fun existsByEmailAndPassword(account: UserAccount): Boolean
}