package io.gijung.springboot.presentation.api.v1.users

import io.gijung.springboot.application.`in`.LoginUseCase
import io.gijung.springboot.application.`in`.SignUpUseCase
import io.gijung.springboot.presentation.api.v1.users.request.LoginRequest
import io.gijung.springboot.presentation.api.v1.users.request.SignUpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val signUpUseCase: SignUpUseCase,
    private val loginUseCase: LoginUseCase
) {
    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<Void> {

        signUpUseCase.signUp(email = request.email, password = request.password, name = request.name)

        return ResponseEntity(HttpStatus.CREATED)
    }

    //TODO : 토큰을 발급해서 반환하도록 변경해야 함
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Void> {

        val token = loginUseCase.login(request.email, request.password)

        return ResponseEntity(HttpStatus.OK)
    }

}