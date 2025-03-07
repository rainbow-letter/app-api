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
    override fun findByPetId(
        petId: Long,
        userId: Long,
        query: RetrieveLetterRequest
    ): List<LetterEntity> {
        val pet =
            petRepository.findById(petId).orElseThrow { EntityNotFoundException(PetEntity::class.jvmName, petId) }

        if (pet.user?.id != userId) throw EntityNotFoundException(PetEntity::class.jvmName, petId)

        return letterRepository.findSlice(PageRequest.of(0, query.limit)) {
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
                    path(LetterEntity::pet).eq(pet),
                )
            ).orderBy(
                path(LetterEntity::id).desc()
            )
        }.filterNotNull()
    }

    override fun findByUserId(
        userId: Long,
        query: RetrieveLetterRequest
    ) = letterRepository.findSlice(PageRequest.of(0, query.limit)) {
        select(
            entity(LetterEntity::class),
        ).from(
            entity(LetterEntity::class),
            fetchJoin(LetterEntity::reply),
            fetchJoin(LetterEntity::user),
            fetchJoin(LetterEntity::pet),
            fetchJoin(PetEntity::favorite)
        ).where(
            and(
                query.after?.let { path(LetterEntity::id).lessThan(it) },
                query.startDate?.let { path(LetterEntity::createdAt).greaterThan(it) },
                query.endDate?.let { path(LetterEntity::createdAt).lessThan(it) },
                path(LetterEntity::user)(UserEntity::id).eq(userId),
            )
        ).orderBy(
            path(LetterEntity::id).desc()
        )
    }.filterNotNull()

    override fun findById(id: Long): LetterEntity =
        letterRepository.findById(id).orElseThrow { EntityNotFoundException(LetterEntity::class.jvmName, id) }
}