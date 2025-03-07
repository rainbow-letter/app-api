package kr.co.rainbowletter.api.letter

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import kr.co.rainbowletter.api.auth.RequireAuthentication
import kr.co.rainbowletter.api.auth.User
import kr.co.rainbowletter.api.data.entity.LetterEntity
import kr.co.rainbowletter.api.exception.EntityNotFoundException
import kr.co.rainbowletter.api.util.extension.getUrlWithoutQuery
import kr.co.rainbowletter.api.util.extension.toQueryString
import org.springdoc.core.annotations.ParameterObject
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import kotlin.reflect.jvm.jvmName

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
    ): LetterResponse {
        val letter = letterService.findById(letterId)
        if (letter.user?.id != user.userEntity.id) throw EntityNotFoundException(LetterEntity::class.jvmName, letterId)
        return LetterResponse(letter)
    }

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
                    it.id!!,
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
                    it.id!!,
                    query.limit,
                ).toQueryString()
            }
        )
    }
}