package io.gijung.aichatbot.auth.domain.exception

import io.gijung.aichatbot.global.error.code.CustomError
import io.gijung.aichatbot.global.error.exception.AbstractCustomException

class PasswordValidationException(error: CustomError, message: String) :
    AbstractCustomException(error = error, message = message)
