package kr.co.rainbowletter.api.sharedLetter

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.co.rainbowletter.api.auth.RequireAuthentication
import kr.co.rainbowletter.api.auth.User
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "shared-letter", description = "무지개에 걸린 편지")
@RestController
@RequestMapping("/api")
class SharedLetterController {

    @Operation(summary = "생성")
    @PostMapping("/pets/{petId}/shared-letters")
    @RequireAuthentication
    fun createSharedLetter(
        @PathVariable petId: String,
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody @ModelAttribute body: SharedLetterRequest
    ) = "asdf"
}