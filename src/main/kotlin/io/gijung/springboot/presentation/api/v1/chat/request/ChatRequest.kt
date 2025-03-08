package io.gijung.springboot.presentation.api.v1.chat.request

data class ChatRequest (
    val message: String,
    val isStreaming: Boolean
)