package kr.co.rainbowletter.api.pet

import kr.co.rainbowletter.api.data.entity.FavoriteEntity
import kr.co.rainbowletter.api.data.entity.PetEntity
import kr.co.rainbowletter.api.util.PaginationInfo
import java.time.Instant
import java.time.LocalDate

data class PetCollectResponse(
    val pets: List<PetResponse>,
    val paginationInfo: PaginationInfo
) {
    constructor(
        pets: List<PetEntity>,
    ) : this(
        pets = pets.map { PetResponse(it) },
        paginationInfo = PaginationInfo()
    )
}

data class PetResponse(
    val id: Long,
    val name: String,
    val deathAnniversary: LocalDate?,
    val image: String,
    val personalities: List<String>,
    val species: String,
    val owner: String,
    val favorite: PetFavoriteResponse,
) {
    constructor(petEntity: PetEntity) : this(
        id = petEntity.id!!,
        name = petEntity.name!!,
        deathAnniversary = petEntity.deathAnniversary,
        image = petEntity.image!!,
        personalities = petEntity.personalities?.split(",") ?: emptyList(),
        species = petEntity.species!!,
        owner = petEntity.owner!!,
        favorite = PetFavoriteResponse(petEntity.favorite!!),
    )
}

data class PetFavoriteResponse(
    val canIncrease: Boolean,
    val dayIncreaseCount: Int,
    val lastIncreasedAt: Instant,
    val total: Int,
) {
    constructor(e: FavoriteEntity) : this(
        canIncrease = e.canIncrease!!,
        dayIncreaseCount = e.dayIncreaseCount!!,
        lastIncreasedAt = e.lastIncreasedAt!!,
        total = e.total!!,
    )
}