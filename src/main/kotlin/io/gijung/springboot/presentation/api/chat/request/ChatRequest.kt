package io.gijung.springboot.presentation.api.chat.request

data class ChatRequest (
    val message: String,
    val isStreaming: Boolean
)