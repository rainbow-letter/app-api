package kr.co.rainbowletter.api.letter

import kr.co.rainbowletter.api.data.entity.HasOwnerExtension.Companion.throwIfDenied
import kr.co.rainbowletter.api.data.entity.LetterEntity
import kr.co.rainbowletter.api.data.entity.UserEntity
import kr.co.rainbowletter.api.data.repository.LetterRepository
import kr.co.rainbowletter.api.data.repository.LetterRepositoryExtension.Companion.getLastCount
import kr.co.rainbowletter.api.data.repository.LetterRepositoryExtension.Companion.retrieve
import kr.co.rainbowletter.api.data.repository.RepositoryExtension.Companion.findByIdOrThrow
import kr.co.rainbowletter.api.util.extension.toHashMap
import org.springframework.stereotype.Service

interface ILetterService {
    fun findByPetId(
        petId: Long,
        userId: Long,
        query: RetrieveLetterRequest
    ): List<Pair<LetterEntity, Long>>

    fun findByUserId(
        userId: Long,
        query: RetrieveLetterRequest
    ): List<Pair<LetterEntity, Long>>

    fun findById(
        id: Long,
        user: UserEntity
    ): LetterEntity
}

data class PetSequence(
    var petId: Long? = 0,
    var count: Long? = 0,
)

@Service
class LetterService(
    private val letterRepository: LetterRepository,
) : ILetterService {
    private fun retrieve(
        query: RetrieveLetterRequest,
        petId: Long? = null,
        userId: Long? = null,
    ): List<Pair<LetterEntity, Long>> {
        val result = letterRepository.retrieve(query, petId, userId)

        val counts = letterRepository
            .getLastCount(query.after, query.endDate, petId, userId)
            .toHashMap(
                key = { o -> o?.petId!! },
                value = { o -> o?.count!! },
            )

        return result.map {
            val seq = counts.computeIfPresent(it.pet?.id!!) { _, seq -> seq - 1 }
            Pair(it, seq!!)
        }
    }

    override fun findByPetId(
        petId: Long,
        userId: Long,
        query: RetrieveLetterRequest
    ): List<Pair<LetterEntity, Long>> {
        return retrieve(
            query = query,
            petId = petId,
        )
    }

    override fun findByUserId(
        userId: Long,
        query: RetrieveLetterRequest
    ): List<Pair<LetterEntity, Long>> {
        return retrieve(
            userId = userId,
            query = query,
        )
    }

    override fun findById(
        id: Long,
        user: UserEntity
    ): LetterEntity =
        letterRepository.findByIdOrThrow(id).throwIfDenied(user)
}