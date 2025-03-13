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
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "legacy-letter", description = "이전 서버 호환용 API")
@RestController
@RequestMapping("/api")
class LegacyLetterController(
    private val letterService: ILetterService,
) {
    @Operation(summary = "ID로 편지 조회")
    @GetMapping("/letters/box")
    @Deprecated("")
    @RequireAuthentication
    fun findById(
        @AuthenticationPrincipal user: User,
        @ParameterObject @Valid @ModelAttribute query: LegacyRetrieveLetterRequest,
        request: HttpServletRequest
    ): LegacyLetterCollectResponse {
        val letters = letterService.findByPetId(query.petId, user.userEntity.id!!, query)
        val lastLetter = letters.lastOrNull()

        return LegacyLetterCollectResponse(
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