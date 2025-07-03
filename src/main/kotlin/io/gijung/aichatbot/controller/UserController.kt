package io.gijung.aichatbot.controller

import io.gijung.aichatbot.controller.request.LoginRequest
import io.gijung.aichatbot.controller.request.SignUpRequest
import io.gijung.aichatbot.controller.response.LoginResponse
import io.gijung.aichatbot.controller.response.SignUpResponse
import io.gijung.aichatbot.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid request: SignUpRequest): ResponseEntity<SignUpResponse> {
        val response = userService.signUp(request)
        return ResponseEntity.status(201).body(response)
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest) : ResponseEntity<LoginResponse> {
        val response = userService.login(request)
        return ResponseEntity.status(200).body(response)
    }

}