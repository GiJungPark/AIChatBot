package io.gijung.aichatbot.auth.infrastructure.id

import io.gijung.aichatbot.auth.application.port.out.UserIdGenerator
import io.gijung.aichatbot.user.domain.modle.vo.UserId
import org.springframework.stereotype.Component
import java.util.*

@Component
class UuidGenerator : UserIdGenerator {
    override fun generate(): UserId {
        return UserId(UUID.randomUUID().toString())
    }
}