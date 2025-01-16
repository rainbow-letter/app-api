package kr.co.rainbowletter.api.auth

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "user")
@RestController
@RequestMapping("/api/users")
class AuthController {

    @GetMapping("@me")
    @RequireAuthentication
    fun me(@AuthenticationPrincipal user: User): User = user
}