package io.gijung.springboot.application.out

import io.gijung.springboot.domain.UserAccount

interface LoginPort {
    fun existsByEmailAndPassword(account: UserAccount): Boolean
}