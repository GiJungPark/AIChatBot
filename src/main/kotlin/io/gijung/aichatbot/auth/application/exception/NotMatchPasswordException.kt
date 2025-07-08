package io.gijung.aichatbot.auth.application.exception

import io.gijung.aichatbot.global.error.exception.AbstractCustomException

class NotMatchPasswordException(message: String) :
    AbstractCustomException(error = UserAccountError.INVALID_CREDENTIALS, message = message)