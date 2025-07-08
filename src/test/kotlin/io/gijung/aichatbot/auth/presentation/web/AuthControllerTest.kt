package io.gijung.aichatbot.auth.presentation.web

import io.gijung.aichatbot.auth.application.port.`in`.LoginCommand
import io.gijung.aichatbot.auth.application.port.`in`.SignUpCommand
import io.gijung.aichatbot.auth.application.service.AuthService
import io.gijung.aichatbot.user.domain.modle.vo.UserId
import io.gijung.aichatbot.auth.infrastructure.jwt.JwtValidator
import io.gijung.aichatbot.auth.presentation.web.request.LoginRequest
import io.gijung.aichatbot.auth.presentation.web.request.SignUpRequest
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.Test

@WebFluxTest(AuthController::class)
@Import(NoSecurityConfig::class)
class AuthControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockitoBean
    lateinit var authService: AuthService

    @MockitoBean
    lateinit var jwtValidator: JwtValidator

    @Test
    fun `회원가입 요청이 유효하면 201 Created를 반환한다`() {
        val email = "test@example.com"
        val password = "strongPassword123"
        val name = "홍길동"
        val userId = UserId("user-id")

        val requestBody = SignUpRequest(
            email = email,
            password = password,
            name = name
        )

        val command = SignUpCommand(
            email = email,
            password = password,
            name = name
        )

        `when`(authService.signUp(command)).thenReturn(userId)

        webTestClient.post()
            .uri("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").isEqualTo(userId.value)
    }

    @Test
    fun `회원 가입시 이메일이 비어있으면 400 Bad Request를 반환한다`() {
        val requestBody = SignUpRequest(
            email = "",
            password = "somePassword",
            name = "홍길동"
        )

        webTestClient.post()
            .uri("/api/auth/signup")
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
            .uri("/api/auth/signup")
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
            .uri("/api/auth/signup")
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
            .uri("/api/auth/signup")
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
        val email = "test@example.com"
        val password = "correctPassword"
        val token = "token"

        val requestBody = LoginRequest(
            email = email,
            password = password,
        )

        val command = LoginCommand(
            email = email,
            password = password
        )

        `when`(authService.login(command)).thenReturn(token)

        webTestClient.post()
            .uri("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.token").isEqualTo(token)
    }

    @Test
    fun `로그인 시 이메일이 비어있으면 400 Bad Request를 반환한다`() {
        val request = LoginRequest(
            email = "",
            password = "somePassword"
        )

        webTestClient.post()
            .uri("/api/auth/login")
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
            .uri("/api/auth/login")
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
            .uri("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.code").isEqualTo("SERVER_002")
            .jsonPath("$.message").isEqualTo("비밀번호는 필수입니다.")
    }

}
