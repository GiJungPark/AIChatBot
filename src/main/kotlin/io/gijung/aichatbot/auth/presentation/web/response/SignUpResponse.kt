package io.gijung.aichatbot.auth.presentation.web.response

import io.gijung.aichatbot.user.domain.modle.vo.UserId

data class SignUpResponse(
    val id: String
) {
    companion object {
        fun from(id: UserId): SignUpResponse = SignUpResponse(id.value)
    }
}
