package io.gijung.springboot.infrastructure.gpt

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.core.io.buffer.DataBuffer
import java.nio.charset.Charset


@Service
class OpenAiClient(
    private val webClient: WebClient.Builder,
    @Value("\${gpt.api.key}") private val apiKey: String
) {

    private val client = webClient.baseUrl("https://api.openai.com/v1").build()

    fun generateResponse(prompt: String, model: String = "gpt-3.5-turbo"): Mono<String> {
        val request = mapOf(
            "model" to model,
            "messages" to listOf(mapOf("role" to "user", "content" to prompt)),
            "temperature" to 0.7
        )

        return client.post()
            .uri("/chat/completions")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer $apiKey")
            .bodyValue(request)
            .retrieve()
            .bodyToMono<Map<String, Any>>()
            .flatMap { response ->
                val choices = response["choices"] as? List<Map<String, Any>> ?: emptyList()
                val message = choices.firstOrNull()?.get("message") as? Map<String, String>
                Mono.justOrEmpty(message?.get("content")).defaultIfEmpty("No response")
            }
    }

    fun generateResponseStream(prompt: String, model: String = "gpt-3.5-turbo"): Flux<String> {
        val request = mapOf(
            "model" to model,
            "messages" to listOf(mapOf("role" to "user", "content" to prompt)),
            "temperature" to 0.7,
            "stream" to true
        )

        return client.post()
            .uri("/chat/completions")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer $apiKey")
            .bodyValue(request)
            .retrieve()
            .bodyToFlux(DataBuffer::class.java)
            .flatMap { buffer ->
                val text = buffer.toString(Charset.defaultCharset())
                Flux.fromIterable(text.lines())
            }
            .filter { it.startsWith("data: ") }
            .mapNotNull { line ->
                try {
                    val json = jacksonObjectMapper().readValue<Map<String, Any>>(line.removePrefix("data: "))
                    val choices = json["choices"] as? List<Map<String, Any>> ?: emptyList()
                    val delta = choices.firstOrNull()?.get("delta") as? Map<String, String>
                    delta?.get("content")
                } catch (e: Exception) {
                    null
                }
            }
    }


}