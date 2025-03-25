package kr.co.rainbowletter.api.letter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.rainbowletter.api.auth.RequireAuthentication
import kr.co.rainbowletter.api.letter.service.ILetterService
import kr.co.rainbowletter.api.letter.service.IPromptService
import org.springframework.web.bind.annotation.*

@Tag(name = "letter")
@RestController
@RequestMapping("/api/admin")
class LetterAdminController(
    private val letterService: ILetterService,
    private val promptService: IPromptService,
) {
    @Operation(summary = "답장 생성", description = "이미 답장이 있으면 재생성")
    @PostMapping("/letters/{letterId}/reply")
    @RequireAuthentication
    fun createReply(
        @PathVariable letterId: Long,
    ) {
        letterService.reply(letterId)
    }

    @Operation(summary = "프롬프트 조회")
    @GetMapping("/prompt")
    @RequireAuthentication
    fun getPrompt() {
        promptService.getDefault()
    }
}