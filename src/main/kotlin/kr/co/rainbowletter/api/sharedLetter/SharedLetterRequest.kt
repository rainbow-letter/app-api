package kr.co.rainbowletter.api.sharedLetter

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import kr.co.rainbowletter.api.data.entity.enums.RecipientType
import java.time.LocalDateTime

data class CreateSharedLetterRequest(
    val content: String,
    val recipientType: RecipientType,
)

data class RetrieveSharedLetterRequest(
    @field:Schema(
        example = "10",
        description = "이전페이지 마지막 ID (첫페이지 요청시 미입력)",
        required = false
    )
    val after: Long? = null,

    @field:Schema(
        example = "10",
        defaultValue = "10",
        description = "한페이지 리소스 수 (최대 1000)",
        required = false
    )
    @field:Min(1, message = "최소 1이어야 합니다.")
    @field:Max(1000, message = "최대 1000을 초과할 수 없습니다.")
    val limit: Int = 10,

    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
)