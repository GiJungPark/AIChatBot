package io.gijung.springboot.application.service

import io.gijung.springboot.infrastructure.gpt.OpenAiClient
import io.gijung.springboot.infrastructure.postgres.chat.ChatEntity
import io.gijung.springboot.infrastructure.postgres.chat.ChatRepository
import io.gijung.springboot.infrastructure.postgres.chat.ThreadEntity
import io.gijung.springboot.infrastructure.postgres.chat.ThreadRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class ChatService(
    private val threadRepository: ThreadRepository,
    private val chatRepository: ChatRepository,
    private val openAiClient: OpenAiClient
) {
    @Transactional
    fun saveChat(userId: Long, question: String): Mono<String> {
        return openAiClient.generateResponse(question)
            .flatMap { answer ->
                val lastThread = threadRepository.findTopByUserIdOrderByCreatedAtDesc(userId)
                val now = LocalDateTime.now()
                val thread = if (lastThread == null || lastThread.createdAt.plusMinutes(30).isBefore(now)) {
                    threadRepository.save(ThreadEntity(userId = userId))
                } else {
                    lastThread
                }
                val chat = ChatEntity(thread = thread, question = question, answer = answer)
                Mono.just(chatRepository.save(chat).answer)
            }
    }

    @Transactional
    fun saveStreamingChat(userId: Long, question: String): Flux<String> {
        val lastThread = threadRepository.findTopByUserIdOrderByCreatedAtDesc(userId)
        val now = LocalDateTime.now()
        val thread = if (lastThread == null || lastThread.createdAt.plusMinutes(30).isBefore(now)) {
            threadRepository.save(ThreadEntity(userId = userId))
        } else {
            lastThread
        }

        val chatBuffer = StringBuilder()
        return openAiClient.generateResponseStream(question)
            .doOnNext { chunk ->
                chatBuffer.append(chunk)
            }
            .doOnComplete {
                val chat = ChatEntity(thread = thread, question = question, answer = chatBuffer.toString())
                chatRepository.save(chat)
            }
    }
}
