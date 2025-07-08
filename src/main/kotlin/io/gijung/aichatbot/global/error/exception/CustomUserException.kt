package io.gijung.aichatbot.global.error.exception

import io.gijung.aichatbot.global.error.code.CustomUserError

class CustomUserException(error: CustomUserError, message: String) :
    AbstractCustomException(error = error, message = message)