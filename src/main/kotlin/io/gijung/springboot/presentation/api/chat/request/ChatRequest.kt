package io.gijung.springboot.presentation.api.chat.request

data class ChatRequest (
    val userId: Long,
    val message: String,
    val isStreaming: Boolean
)