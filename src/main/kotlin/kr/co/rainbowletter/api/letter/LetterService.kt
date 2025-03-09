package kr.co.rainbowletter.api.letter

import kr.co.rainbowletter.api.data.entity.LetterEntity
import kr.co.rainbowletter.api.data.entity.PetEntity
import kr.co.rainbowletter.api.data.entity.UserEntity
import kr.co.rainbowletter.api.data.repository.LetterRepository
import kr.co.rainbowletter.api.data.repository.PetRepository
import kr.co.rainbowletter.api.exception.EntityNotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import kotlin.reflect.jvm.jvmName

interface ILetterService {
    fun findByPetId(
        petId: Long,
        userId: Long,
        query: RetrieveLetterRequest
    ): List<LetterEntity>

    fun findByUserId(
        userId: Long,
        query: RetrieveLetterRequest
    ): List<LetterEntity>

    fun findById(id: Long): LetterEntity
}

@Service
class LetterService(
    private val letterRepository: LetterRepository,
    private val petRepository: PetRepository
) : ILetterService {
    private fun retrieve(
        query: RetrieveLetterRequest,
        petId: Long? = null,
        userId: Long? = null,
    ): List<LetterEntity> = letterRepository.findSlice(PageRequest.of(0, query.limit)) {
        select(
            entity(LetterEntity::class),
        ).from(
            entity(LetterEntity::class),
            fetchJoin(LetterEntity::reply),
            fetchJoin(LetterEntity::user),
            fetchJoin(LetterEntity::pet),
        ).where(
            and(
                query.after?.let { path(LetterEntity::id).lessThan(it) },
                query.startDate?.let { path(LetterEntity::createdAt).greaterThan(it) },
                query.endDate?.let { path(LetterEntity::createdAt).lessThan(it) },
                petId?.let { path(LetterEntity::pet)(PetEntity::id).eq(petId) },
                userId?.let { path(LetterEntity::user)(UserEntity::id).eq(userId) },
            )
        ).orderBy(
            path(LetterEntity::id).desc()
        )
    }.filterNotNull()

    override fun findByPetId(
        petId: Long,
        userId: Long,
        query: RetrieveLetterRequest
    ): List<LetterEntity> {
        val pet =
            petRepository.findById(petId).orElseThrow { EntityNotFoundException(PetEntity::class.jvmName, petId) }

        if (pet.user?.id != userId) throw EntityNotFoundException(PetEntity::class.jvmName, petId)

        val result = retrieve(
            query = query,
            petId = pet.id,
        )

        if (result.isEmpty()) return result

        val maxId = result.maxBy { o -> o.id!! }.id!!
        val count = letterRepository.findAll(PageRequest.of(0, 1)) {
            select(
                count(LetterEntity::id)
            ).from(entity(LetterEntity::class))
        }.firstOrNull() ?: 0

        return result
    }

    override fun findByUserId(
        userId: Long,
        query: RetrieveLetterRequest
    ): List<LetterEntity> {
        return retrieve(
            query = query,
            petId = userId,
        )
    }

    override fun findById(id: Long): LetterEntity =
        letterRepository.findById(id).orElseThrow { EntityNotFoundException(LetterEntity::class.jvmName, id) }
}