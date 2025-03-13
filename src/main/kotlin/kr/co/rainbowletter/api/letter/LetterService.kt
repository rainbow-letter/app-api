package kr.co.rainbowletter.api.letter

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import kr.co.rainbowletter.api.data.entity.HasOwnerExtension.Companion.throwIfDenied
import kr.co.rainbowletter.api.data.entity.LetterEntity
import kr.co.rainbowletter.api.data.entity.PetEntity
import kr.co.rainbowletter.api.data.entity.UserEntity
import kr.co.rainbowletter.api.data.repository.JpqlExtension
import kr.co.rainbowletter.api.data.repository.LetterRepository
import kr.co.rainbowletter.api.data.repository.RepositoryExtension.Companion.findByIdOrThrow
import kr.co.rainbowletter.api.util.extension.toHashMap
import org.springframework.data.domain.PageRequest
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
        val result = letterRepository.findSlice(PageRequest.of(0, query.limit)) {
            jpql(JpqlExtension()) {
                select(
                    entity(LetterEntity::class),
                ).from(
                    entity(LetterEntity::class),
                    leftJoin(LetterEntity::reply),
                    fetchJoin(LetterEntity::user),
                    fetchJoin(LetterEntity::pet),
                    fetchJoin(PetEntity::favorite),
                ).where(
                    letterRetrieve(query, petId, userId)
                ).orderBy(
                    path(LetterEntity::id).desc()
                )
            }
        }.filterNotNull()

        val counts = letterRepository.findAll {
            jpql(JpqlExtension()) {
                select<PetSequence>(
                    path(LetterEntity::pet)(PetEntity::id),
                    count(LetterEntity::id)
                ).from(
                    entity(LetterEntity::class),
                ).where(
                    letterRetrieve(query, petId, userId)
                ).groupBy(
                    path(LetterEntity::pet)(PetEntity::id),
                )
            }
        }.toHashMap(
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