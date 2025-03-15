package kr.co.rainbowletter.api.sharedLetter

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.co.rainbowletter.api.auth.RequireAuthentication
import kr.co.rainbowletter.api.auth.User
import kr.co.rainbowletter.api.data.entity.enums.RecipientType
import kr.co.rainbowletter.api.pet.PetResponse
import kr.co.rainbowletter.api.util.PaginationInfo
import org.springdoc.core.annotations.ParameterObject
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "shared-letter", description = "무지개에 걸린 편지")
@RestController
@RequestMapping("/api")
class SharedLetterController(
    private val sharedLetterService: ISharedLetterService
) {
    @Operation(summary = "생성")
    @PostMapping("/pets/{petId}/shared-letters")
    @RequireAuthentication
    fun createSharedLetter(
        @PathVariable petId: Long,
        @AuthenticationPrincipal user: User,
        @RequestBody @Valid payload: CreateSharedLetterRequest,
    ) = SharedLetterResponse(sharedLetterService.create(petId, user.userEntity, payload))

    @Operation(summary = "내가 작성한 편지 조회")
    @GetMapping("/users/@me/shared-letters")
    @RequireAuthentication
    fun retrieve(
        @AuthenticationPrincipal user: User,
        @ParameterObject @Valid @ModelAttribute query: RetrieveSharedLetterByUserIdRequest,
    ): SharedLetterCollectResponse =
        SharedLetterCollectResponse(sharedLetterService.retrieve(user.userEntity, query), "")

    @Operation(summary = "조회")
    @GetMapping("/shared-letters")
    fun retrieve(
        @ParameterObject @Valid @ModelAttribute query: RetrieveSharedLetterRequest,
    ): SharedLetterCollectResponse = SharedLetterCollectResponse(sharedLetterService.retrieve(query), "")

    @Operation(summary = "샘플 편지 조회")
    @GetMapping("/shared-letters/sample")
    fun retrieve(): SharedLetterCollectResponse = SharedLetterCollectResponse(
        listOf(
            SharedLetterResponse(
                0,
                "엄마를 만나\n행복했니?\n엄마는 미키 덕분에\n정말 행복했어",
                RecipientType.PET,
                PetResponse("미키")
            ),
            SharedLetterResponse(
                0,
                "내가 보고 싶을 때\n밤하늘을 봐줘\n별이 되어 누나를\n지켜보고 있으니까!",
                RecipientType.OWNER,
                PetResponse("우주")
            ),
            SharedLetterResponse(
                0,
                "코코야 엄마가\n항상 사랑해\n다음에도 내\n아들이 되어줘",
                RecipientType.PET,
                PetResponse("코코")
            ),
            SharedLetterResponse(
                0,
                "나는 항상 언니\n마음 속에 있다는 거\n잊지 마! 항상 언니를 응원해.",
                RecipientType.OWNER,
                PetResponse("무지")
            ),
        ), PaginationInfo(null)
    )
}