package io.gijung.aichatbot.auth.application.exception

import io.gijung.aichatbot.exception.exception.AbstractCustomException

class NotMatchPasswordException(message: String) :
    AbstractCustomException(error = UserAccountError.INVALID_CREDENTIALS, message = message)