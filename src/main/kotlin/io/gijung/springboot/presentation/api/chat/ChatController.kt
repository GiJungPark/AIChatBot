package io.gijung.springboot.presentation.api.chat

import io.gijung.springboot.application.service.ChatService
import io.gijung.springboot.infrastructure.gpt.OpenAiClient
import io.gijung.springboot.presentation.api.chat.request.ChatRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux


@RestController
@RequestMapping("/api/v1/chats")
class ChatController(
    private val gptClient: OpenAiClient,
    private val chatService: ChatService
) {

    @PostMapping(produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    @ResponseBody
    fun chat(@RequestBody request: ChatRequest): Flux<String> {
        return if (request.isStreaming) {
            chatService.saveStreamingChat(request.userId, request.message)
        } else {
            chatService.saveChat(request.userId, request.message).flux()
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