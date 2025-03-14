package kr.co.rainbowletter.api.sharedLetter

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import kr.co.rainbowletter.api.data.entity.enums.RecipientType
import java.time.LocalDateTime

data class CreateSharedLetterRequest(
    @field:Schema(
        example = "엄마를 만나 행복했니? 엄마는 미키 덕분에정말 행복했어",
        description = "본문",
        required = true
    )
    val content: String,

    @field:Schema(
        example = "0",
        description = "내가보낸거: 0 내가받은거: 1",
        required = true
    )
    val recipientType: RecipientType,
)

data class RetrieveSharedLetterByUserIdRequest(
    @field:Schema(
        example = "2025-02-01 00:00:00.000",
        description = "검색 시작일 (작성일)",
        required = false
    )
    val startDate: LocalDateTime? = null,

    @field:Schema(
        example = "2025-02-01 00:00:00.000",
        description = "검색 종료일 (작성일)",
        required = false
    )
    val endDate: LocalDateTime? = null,
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