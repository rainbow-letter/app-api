package kr.co.rainbowletter.api.letter

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import kr.co.rainbowletter.api.auth.RequireAuthentication
import kr.co.rainbowletter.api.auth.User
import kr.co.rainbowletter.api.util.extension.getUrlWithoutQuery
import kr.co.rainbowletter.api.util.extension.toQueryString
import org.springdoc.core.annotations.ParameterObject
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "letter")
@RestController
@RequestMapping("/api")
class LetterController(
    private val letterService: ILetterService,
) {
    @Operation(summary = "ID로 편지 조회")
    @GetMapping("/letters/{letterId}")
    @RequireAuthentication
    fun findById(
        @PathVariable letterId: Long,
        @AuthenticationPrincipal user: User,
    ): LetterResponse = LetterResponse(letterService.findById(letterId, user.userEntity), 0)

    @Operation(summary = "펫ID 로 작성한 편지 조회")
    @GetMapping("/pets/{petId}/letters")
    @RequireAuthentication
    fun findByPetId(
        @PathVariable petId: Long,
        @AuthenticationPrincipal user: User,
        @ParameterObject @Valid @ModelAttribute query: RetrieveLetterRequest,
        request: HttpServletRequest
    ): LetterCollectResponse {
        val letters = letterService.findByPetId(petId, user.userEntity.id!!, query)
        val lastLetter = letters.lastOrNull()

        return LetterCollectResponse(
            letters,
            lastLetter?.let {
                request.getUrlWithoutQuery() + "?" + RetrieveLetterRequest(
                    it.first.id!!,
                    query.limit,
                ).toQueryString()
            }
        )
    }

    @Operation(summary = "로그인한 사용자가 작성한 편지 조회")
    @GetMapping("/letters")
    @RequireAuthentication
    fun findByUserIdId(
        @AuthenticationPrincipal user: User,
        @ParameterObject @Valid @ModelAttribute query: RetrieveLetterRequest,
        request: HttpServletRequest
    ): LetterCollectResponse {
        val letters = letterService.findByUserId(user.userEntity.id!!, query)
        val lastLetter = letters.lastOrNull()

        return LetterCollectResponse(
            letters,
            lastLetter?.let {
                request.getUrlWithoutQuery() + "?" + RetrieveLetterRequest(
                    it.first.id!!,
                    query.limit,
                ).toQueryString()
            }
        )
    }
}