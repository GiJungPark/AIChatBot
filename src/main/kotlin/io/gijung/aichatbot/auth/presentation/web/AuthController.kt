package io.gijung.aichatbot.auth.presentation.web

import io.gijung.aichatbot.auth.application.port.`in`.LoginCommand
import io.gijung.aichatbot.auth.application.port.`in`.SignUpCommand
import io.gijung.aichatbot.auth.application.service.AuthService
import io.gijung.aichatbot.auth.presentation.web.request.LoginRequest
import io.gijung.aichatbot.auth.presentation.web.request.SignUpRequest
import io.gijung.aichatbot.auth.presentation.web.response.LoginResponse
import io.gijung.aichatbot.auth.presentation.web.response.SignUpResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid request: SignUpRequest): ResponseEntity<SignUpResponse> {
        val command = SignUpCommand(
            email = request.email,
            password = request.password,
            name = request.name,
        )

        val userId = authService.signUp(command)

        return ResponseEntity.status(201)
            .body(SignUpResponse(userId.value))
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<LoginResponse> {
        val command = LoginCommand(
            email = request.email,
            password = request.password,
        )

        val token = authService.login(command)

        return ResponseEntity.status(200)
            .body(LoginResponse(token))
    }
}