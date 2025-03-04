package kr.co.rainbowletter.api.letter

import io.swagger.v3.oas.annotations.media.Schema
import org.springdoc.core.annotations.ParameterObject

@ParameterObject
class RetrieveLetterRequest(
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
    val limit: Int = 10,
)
