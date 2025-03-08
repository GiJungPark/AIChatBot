package io.gijung.springboot.presentation.api.v1.users

import io.gijung.springboot.application.`in`.LoginUseCase
import io.gijung.springboot.application.`in`.SignUpUseCase
import io.gijung.springboot.domain.user.Role
import io.gijung.springboot.presentation.api.v1.users.request.LoginRequest
import io.gijung.springboot.presentation.api.v1.users.request.SignUpRequest
import io.gijung.springboot.support.security.JwtUtil
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
    private val loginUseCase: LoginUseCase,
    private val jwtUtil: JwtUtil
) {
    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<Void> {

        signUpUseCase.signUp(email = request.email, password = request.password, name = request.name)

        return ResponseEntity(HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<String> {

        if(loginUseCase.accountValidate(request.email, request.password)) {
            return ResponseEntity.ok().body(jwtUtil.generateToken(request.email, Role.MEMBER))
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 혹은 비밀번호가 잘못되었습니다.")
    }

}