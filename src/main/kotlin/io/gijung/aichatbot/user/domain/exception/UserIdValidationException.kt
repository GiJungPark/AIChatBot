package io.gijung.aichatbot.user.domain.exception

import io.gijung.aichatbot.global.error.code.CustomError
import io.gijung.aichatbot.global.error.exception.AbstractCustomException

class UserIdValidationException (error: CustomError, message: String) :
    AbstractCustomException(error = error, message = message)