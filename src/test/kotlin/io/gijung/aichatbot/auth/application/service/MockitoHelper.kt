package io.gijung.aichatbot.auth.application.service

import io.gijung.aichatbot.user.domain.modle.vo.UserId
import org.mockito.Mockito

object MockitoHelper {
    fun anyUserId(): UserId = Mockito.any<UserId>() ?: UserId("mock-id")
}