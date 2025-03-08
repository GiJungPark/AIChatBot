package io.gijung.springboot.presentation.api.chat

import io.gijung.springboot.application.service.ChatService
import io.gijung.springboot.infrastructure.gpt.OpenAiClient
import io.gijung.springboot.presentation.api.chat.request.ChatRequest
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux


@RestController
@RequestMapping("/api/v1/chats")
class ChatController(
    private val chatService: ChatService
) {
    @PostMapping(produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    @ResponseBody
    fun chat(@RequestBody request: ChatRequest, httpRequest: HttpServletRequest): Flux<String> {
        val userId = httpRequest.getAttribute("userId") as? String
            ?: throw IllegalStateException("User ID not found")

        return if (request.isStreaming) {
            chatService.saveStreamingChat(userId.toLong(), request.message)
        } else {
            chatService.saveChat(userId.toLong(), request.message).flux()
        }
    }

//    @GetMapping
//    fun getChats(
//        @RequestParam userId: String,
//        @RequestParam page: Int,
//        @RequestParam size: Int,
//        @RequestParam sort: String
//    ): Flux<ThreadWithChats> {
//        return chatService.getChats(userId, page, size, sort)
//    }

//    @DeleteMapping("/threads/{threadId}")
//    fun deleteThread(@PathVariable threadId: String, @RequestParam userId: String): Mono<Void> {
//        return chatService.deleteThread(threadId, userId)
//    }
}