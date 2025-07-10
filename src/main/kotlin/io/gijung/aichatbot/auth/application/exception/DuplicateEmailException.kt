package io.gijung.aichatbot.auth.application.exception

import io.gijung.aichatbot.global.error.exception.AbstractCustomException

class DuplicateEmailException(message: String) :
    AbstractCustomException(error = UserAccountError.DUPLICATE_EMAIL, message = message)