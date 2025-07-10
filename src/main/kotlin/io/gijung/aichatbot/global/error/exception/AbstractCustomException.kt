package io.gijung.aichatbot.global.error.exception

import io.gijung.aichatbot.global.error.code.CustomError

abstract class AbstractCustomException(
    val error: CustomError,
    override val message: String
) : RuntimeException(message)