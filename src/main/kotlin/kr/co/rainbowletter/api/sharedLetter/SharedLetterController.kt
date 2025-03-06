package kr.co.rainbowletter.api.sharedLetter

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.rainbowletter.api.auth.RequireAuthentication
import kr.co.rainbowletter.api.auth.User
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "shared-letter", description = "무지개에 걸린 편지")
@RestController
@RequestMapping("/api/shared-letter")
class SharedLetterController {

    @Operation(summary = "생성")
    @GetMapping
    @RequireAuthentication
    fun createSharedLetter(
        @AuthenticationPrincipal user: User,
    ) {

    }
}