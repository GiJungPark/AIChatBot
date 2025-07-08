package io.gijung.aichatbot.auth.domain.model.vo

import com.fasterxml.jackson.annotation.JsonCreator

@JvmInline
value class Password private constructor(val value: String) {
    companion object {
        @JsonCreator
        @JvmStatic
        fun from(password: String): Password {
            return Password(password)
        }

        operator fun invoke(password: String): Password = from(password)
    }

}