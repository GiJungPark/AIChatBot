package io.gijung.aichatbot.auth.domain.exception

import io.gijung.aichatbot.exception.error.CustomError
import io.gijung.aichatbot.exception.exception.AbstractCustomException

class UserIdValidationException (error: CustomError, message: String) :
    AbstractCustomException(error = error, message = message)