package kr.co.rainbowletter.api.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.rainbowletter.api.auth.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "user")
@RestController
@RequestMapping("/api/users")
class AuthController(
    private val userService: UserService,
) {

    @GetMapping("@me")
    @RequireAuthentication
    @Operation(summary = "현재 로그인된 회원 조회")
    fun me(@AuthenticationPrincipal user: User): UserResponse {
        val userEntity = userService.findByEmail(user.email)
        return UserResponse(
            email = userEntity.email!!,
            phoneNumber = userEntity.phoneNumber!!,
            role = userEntity.role!!,
        )
    }
}

data class UserResponse(
    val email: String,
    val phoneNumber: String,
    val role: String,
)