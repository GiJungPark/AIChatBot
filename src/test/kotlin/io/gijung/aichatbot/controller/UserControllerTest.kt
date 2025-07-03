package io.gijung.aichatbot.controller

import io.gijung.aichatbot.auth.JwtValidator
import io.gijung.aichatbot.config.SecurityConfig
import io.gijung.aichatbot.controller.request.LoginRequest
import io.gijung.aichatbot.controller.request.SignUpRequest
import io.gijung.aichatbot.controller.response.LoginResponse
import io.gijung.aichatbot.service.UserService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(UserController::class)
@Import(NoSecurityConfig::class)
class UserControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockitoBean
    lateinit var userService: UserService

    @MockitoBean
    lateinit var jwtValidator: JwtValidator

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
    fun `회원 가입시 이메일이 비어있으면 400 Bad Request를 반환한다`() {
        val requestBody = SignUpRequest(
            email = "",
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
            .jsonPath("$.message").isEqualTo("이메일은 필수입니다.")
    }

    @Test
    fun `회원 가입시 이메일 형식이 잘못되면 400 Bad Request를 반환한다`() {
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
    fun `회원 가입시 비밀번호가 비어있으면 400 Bad Request를 반환한다`() {
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
    fun `회원 가입시 이름이 비어있으면 400 Bad Request를 반환한다`() {
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

    @Test
    fun `로그인 요청이 유효하면 200 OK와 토큰을 반환한다`() {
        val requestBody = LoginRequest(
            email = "test@example.com",
            password = "correctPassword"
        )

        val expectedResponse = LoginResponse(
            token = "token"
        )

        `when`(userService.login(requestBody)).thenReturn(expectedResponse)

        webTestClient.post()
            .uri("/api/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.token").isEqualTo("token")
    }

    @Test
    fun `로그인 시 이메일이 비어있으면 400 Bad Request를 반환한다`() {
        val request = LoginRequest(
            email = "",
            password = "somePassword"
        )

        webTestClient.post()
            .uri("/api/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.code").isEqualTo("SERVER_002")
            .jsonPath("$.message").isEqualTo("이메일은 필수입니다.")
    }

    @Test
    fun `로그인 시 이메일 형식이 잘못되면 400 Bad Request를 반환한다`() {
        val request = LoginRequest(
            email = "invalid-email",
            password = "validPassword123"
        )

        webTestClient.post()
            .uri("/api/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.code").isEqualTo("SERVER_002")
            .jsonPath("$.message").isEqualTo("이메일 형식이 잘못되었습니다.")
    }

    @Test
    fun `로그인 시 비밀번호가 비어있으면 400 Bad Request를 반환한다`() {
        val request = LoginRequest(
            email = "test@example.com",
            password = " "
        )

        webTestClient.post()
            .uri("/api/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.code").isEqualTo("SERVER_002")
            .jsonPath("$.message").isEqualTo("비밀번호는 필수입니다.")
    }

}