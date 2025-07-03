package io.gijung.aichatbot.exception.exception

import io.gijung.aichatbot.exception.error.CustomError

abstract class AbstractCustomException(
    val error: CustomError,
    override val message: String
) : RuntimeException(message)