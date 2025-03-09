package kr.co.rainbowletter.api.letter

import kr.co.rainbowletter.api.data.entity.LetterEntity
import kr.co.rainbowletter.api.data.entity.ReplyReadStatus
import kr.co.rainbowletter.api.util.PaginationInfo
import kr.co.rainbowletter.api.util.extension.firstOrThrow
import java.time.LocalDateTime

data class LegacyLetterCollectResponse(
    val letters: List<LegacyLetterResponse>,
    val paginationInfo: PaginationInfo
) {
    constructor(
        letterEntities: List<LetterEntity>,
        next: String?
    ) : this(
        letters = letterEntities.map { LegacyLetterResponse(it) },
        paginationInfo = PaginationInfo(next)
    )
}

data class LegacyLetterResponse(
    val id: Long,
    val number: Int,
    val summary: String,
    val status: String,
    val petName: String,
    val readStatus: ReplyReadStatus,
    val createdAt: LocalDateTime
) {
    constructor(e: LetterEntity) : this(
        id = e.id!!,
        number = e.number!!,
        summary = e.summary!!,
        status = e.status!!,
        petName = e.pet?.name!!,
        readStatus = e.reply!!.firstOrThrow { Exception("답변 없음") }.readStatus!!,
        createdAt = e.createdAt!!
    )
}