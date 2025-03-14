package kr.co.rainbowletter.api.sharedLetter

import kr.co.rainbowletter.api.data.entity.SharedLetterEntity
import kr.co.rainbowletter.api.data.entity.enums.RecipientType
import kr.co.rainbowletter.api.pet.PetResponse
import kr.co.rainbowletter.api.util.PaginationInfo

data class SharedLetterCollectResponse(
    val sharedLetters: List<SharedLetterResponse>,
    val paginationInfo: PaginationInfo,
) {
    constructor(
        sharedLetters: List<SharedLetterEntity>,
        next: String?
    ) : this(
        sharedLetters = sharedLetters.map { SharedLetterResponse(it) },
        paginationInfo = PaginationInfo(next)
    )
}

data class SharedLetterResponse(
    val id: Long,
    val content: String,
    val recipientType: RecipientType,
    val pet: PetResponse?
) {
    constructor(e: SharedLetterEntity) : this(
        id = e.id!!,
        content = e.content!!,
        recipientType = e.recipientType!!,
        pet = PetResponse(e.pet!!)
    )
}