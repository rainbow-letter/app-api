package kr.co.rainbowletter.api.sharedLetter

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.co.rainbowletter.api.auth.RequireAuthenticationWithAdmin
import kr.co.rainbowletter.api.auth.User
import org.springdoc.core.annotations.ParameterObject
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "shared-letter", description = "무지개에 걸린 편지")
@RestController
@RequestMapping("/api/admin")
class SharedLetterAdminController(
    private val sharedLetterService: ISharedLetterService
) {
    @Operation(summary = "삭제")
    @DeleteMapping("/shared-letters/{letterId}")
    @RequireAuthenticationWithAdmin
    fun delete(@PathVariable letterId: Long) = sharedLetterService.delete(letterId)

    @Operation(summary = "조회")
    @GetMapping("/shared-letters")
    fun retrieve(
        @AuthenticationPrincipal user: User?,
        @ParameterObject @Valid @ModelAttribute query: RetrieveSharedLetterRequest,
    ): SharedLetterCollectResponse =
        SharedLetterCollectResponse(sharedLetterService.retrieve(user?.userEntity, query), "")
}