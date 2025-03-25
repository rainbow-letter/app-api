package kr.co.rainbowletter.api.letter.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springdoc.core.annotations.ParameterObject

@ParameterObject
class LegacyRetrieveLetterRequest(
    @field:Schema(
        example = "4",
        required = false
    )
    val petId: Long,

    after: Long? = null,
    limit: Int = 10,
) : RetrieveLetterRequest(after, limit)