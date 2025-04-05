package kr.co.rainbowletter.api.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.co.rainbowletter.api.auth.service.IUserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "user")
@RestController
@RequestMapping("/api/admin/users")
class AdminAuthController(
    private val userService: IUserService,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {
    @PostMapping("mock")
    @RequireAuthenticationWithAdmin
    @Operation(summary = "사용자 토큰 발급")
    fun mock(
        @RequestBody @Valid query: MockAuthRequest,
    ): MockAuthResponse {
        val token = jwtAuthenticationFilter.generateToken(
            query.email,
            Role.ROLE_USER,
        )

        return MockAuthResponse(
            token,
            "/oauth/success?token=$token"
        )
    }
}

data class MockAuthRequest(val email: String)

data class MockAuthResponse(
    val token: String,
    val redirectUrl: String,
)