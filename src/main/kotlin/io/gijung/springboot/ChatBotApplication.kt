package io.gijung.springboot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChatBotApplication

fun main(args: Array<String>) {
    runApplication<ChatBotApplication>(*args)
}
