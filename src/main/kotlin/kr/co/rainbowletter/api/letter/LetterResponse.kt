package kr.co.rainbowletter.api.letter

import kr.co.rainbowletter.api.data.entity.LetterEntity
import kr.co.rainbowletter.api.pet.PetResponse
import kr.co.rainbowletter.api.util.PaginationInfo
import java.time.Instant

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
    val createdAt: Instant,
    val pet: PetResponse,
    val summary: String,
    val content: String,
    val shareLink: String,
    val image: String?,
) {
    constructor(letterEntity: LetterEntity) : this(
        id = letterEntity.id!!,
        createdAt = letterEntity.createdAt!!,
        pet = PetResponse(letterEntity.pet!!),
        summary = letterEntity.summary!!,
        content = letterEntity.content!!,
        shareLink = letterEntity.shareLink!!,
        image = letterEntity.image,
    )
}