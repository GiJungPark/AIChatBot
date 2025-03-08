package io.gijung.springboot.infrastructure.postgres.user

import io.gijung.springboot.domain.Role
import io.gijung.springboot.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "users")
data class UserEntity(
    // TODO: 자동증가의 경우, 스케일 아웃을 고려하는 상황에서는 좋은 선택지는 아니기 때문에 추후 ID 생성기를 제작해야힘
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, columnDefinition = "timestamp", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role
) {
    constructor() : this(email = "", password = "", name = "", role = Role.MEMBER) {
    }

    companion object {
        fun of(user: User): UserEntity {
            return UserEntity(
                email = user.userAccount.email,
                password = user.userAccount.password,
                name = user.name,
                role = user.role,
            )
        }
    }
}