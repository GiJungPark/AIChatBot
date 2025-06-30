package io.gijung.aichatbot.controller

import io.gijung.aichatbot.controller.request.SignUpRequest
import io.gijung.aichatbot.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(UserController::class)
class UserControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockitoBean
    lateinit var userService: UserService

    @Test
    fun `회원가입 요청이 유효하면 201 Created를 반환한다`() {
        val requestBody = SignUpRequest(
            email = "test@example.com",
            password = "strongPassword123",
            name = "홍길동"
        )

        webTestClient.post()
            .uri("/api/users/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isCreated
    }

    @Test
    fun `이메일이 비어있으면 400 Bad Request를 반환한다`() {
        // given
        val requestBody = SignUpRequest(
            email = "",
            password = "somePassword",
            name = "홍길동"
        )

        // when & then
        webTestClient.post()
            .uri("/api/users/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.code").isEqualTo("SERVER_002")
            .jsonPath("$.message").isEqualTo("이메일은 필수입니다.")
    }

    @Test
    fun `이메일 형식이 잘못되면 400 Bad Request를 반환한다`() {
        val requestBody = SignUpRequest(
            email = "invalid-email",
            password = "somePassword",
            name = "홍길동"
        )

        webTestClient.post()
            .uri("/api/users/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.code").isEqualTo("SERVER_002")
            .jsonPath("$.message").isEqualTo("이메일 형식이 잘못되었습니다.")
    }

    @Test
    fun `비밀번호가 비어있으면 400 Bad Request를 반환한다`() {
        val requestBody = SignUpRequest(
            email = "test@example.com",
            password = " ",
            name = "홍길동"
        )

        webTestClient.post()
            .uri("/api/users/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.code").isEqualTo("SERVER_002")
            .jsonPath("$.message").isEqualTo("비밀번호는 필수입니다.")
    }

    @Test
    fun `이름이 비어있으면 400 Bad Request를 반환한다`() {
        val requestBody = SignUpRequest(
            email = "test@example.com",
            password = "somePassword",
            name = " "
        )

        webTestClient.post()
            .uri("/api/users/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.code").isEqualTo("SERVER_002")
            .jsonPath("$.message").isEqualTo("이름은 필수입니다.")
    }
}