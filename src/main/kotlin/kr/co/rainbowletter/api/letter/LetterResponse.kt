package kr.co.rainbowletter.api.letter

import kr.co.rainbowletter.api.data.entity.*
import kr.co.rainbowletter.api.pet.PetResponse
import kr.co.rainbowletter.api.util.PaginationInfo
import java.time.LocalDateTime

data class LetterCollectResponse(
    val letters: List<LetterResponse>,
    val paginationInfo: PaginationInfo
) {
    constructor(
        letterEntities: List<LetterEntity>,
        next: String?
    ) : this(
        letters = letterEntities.map { LetterResponse(it) },
        paginationInfo = PaginationInfo(next)
    )
}

data class LetterResponse(
    val id: Long,
    val createdAt: LocalDateTime,
    val pet: PetResponse,
    val summary: String,
    val content: String,
    val shareLink: String,
    val image: String?,
    val reply: LetterReplyResponse?,
) {
    constructor(e: LetterEntity) : this(
        id = e.id!!,
        createdAt = e.createdAt!!,
        pet = PetResponse(e.pet!!),
        summary = e.summary!!,
        content = e.content!!,
        shareLink = e.shareLink!!,
        image = e.image,
        reply = e.reply?.firstOrNull()?.let { LetterReplyResponse(it) }
    )
}

data class LetterReplyResponse(
    val createdAt: LocalDateTime,
    val inspection: Boolean,
    val inspectionTime: LocalDateTime,
    val promptType: PromptType,
    val readStatus: ReplyReadStatus,
    val status: ReplyStatus,
    val submitTime: LocalDateTime?,
    val content: String,
    val summary: String,
) {
    constructor(e: ReplyEntity) : this(
        createdAt = e.createdAt!!,
        inspection = e.inspection!!,
        inspectionTime = e.inspectionTime!!,
        promptType = e.promptType!!,
        readStatus = e.readStatus!!,
        status = e.status!!,
        submitTime = e.submitTime,
        content = e.content!!,
        summary = e.summary!!,
    )
}