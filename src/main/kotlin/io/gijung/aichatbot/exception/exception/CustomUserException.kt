package io.gijung.aichatbot.exception.exception

import io.gijung.aichatbot.exception.error.CustomUserError

class CustomUserException(error: CustomUserError, message: String) :
    AbstractCustomException(error = error, message = message)