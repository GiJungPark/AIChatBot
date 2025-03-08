package io.gijung.springboot.application.out

import io.gijung.springboot.domain.User

interface SignUpPort {
    fun createUser(user: User)
}