package kr.co.rainbowletter.api.letter

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springdoc.core.annotations.ParameterObject
import java.time.LocalDateTime

@ParameterObject
open class RetrieveLetterRequest(
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
    @Min(1, message = "최소 1이어야 합니다.")
    @Max(1000, message = "최대 1000을 초과할 수 없습니다.")
    val limit: Int = 10,

    @field:Schema(
        example = "2025-02-01T00:00:00Z",
        defaultValue = "null",
        description = "검색 시작일시",
        required = false
    )
    val startDate: LocalDateTime? = null,

    @field:Schema(
        example = "2025-02-01T23:59:59Z",
        defaultValue = "null",
        description = "한검색 종료일시",
        required = false
    )
    val endDate: LocalDateTime? = null,
)
