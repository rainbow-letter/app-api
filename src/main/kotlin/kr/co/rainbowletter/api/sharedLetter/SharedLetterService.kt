package kr.co.rainbowletter.api.sharedLetter

import kr.co.rainbowletter.api.data.entity.HasOwnerExtension.Companion.throwIfDenied
import kr.co.rainbowletter.api.data.entity.LetterEntity
import kr.co.rainbowletter.api.data.entity.SharedLetterEntity
import kr.co.rainbowletter.api.data.entity.UserEntity
import kr.co.rainbowletter.api.data.repository.PetRepository
import kr.co.rainbowletter.api.data.repository.RepositoryExtension.Companion.findByIdOrThrow
import kr.co.rainbowletter.api.data.repository.SharedLetterRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

interface ISharedLetterService {
    fun create(
        petId: Long,
        user: UserEntity,
        payload: CreateSharedLetterRequest,
    ): SharedLetterEntity

    fun retrieve(
        user: UserEntity,
        query: RetrieveSharedLetterByUserIdRequest,
    ): List<SharedLetterEntity>

    fun retrieve(
        user: UserEntity?,
        query: RetrieveSharedLetterRequest
    ): List<SharedLetterEntity>
}

@Service
class SharedLetterService(
    private val sharedLetterRepository: SharedLetterRepository,
    private val petRepository: PetRepository,
) : ISharedLetterService {
    override fun create(
        petId: Long,
        user: UserEntity,
        payload: CreateSharedLetterRequest,
    ): SharedLetterEntity {
        val pet = petRepository.findByIdOrThrow(petId).throwIfDenied(user)
        val sharedLetter = SharedLetterEntity().apply {
            recipientType = payload.recipientType
            content = payload.content
            this.pet = pet
            this.user = user
        }

        return sharedLetterRepository.save(sharedLetter)
    }

    override fun retrieve(
        user: UserEntity,
        query: RetrieveSharedLetterByUserIdRequest,
    ): List<SharedLetterEntity> =
        sharedLetterRepository.findAll {
            select(
                entity(SharedLetterEntity::class),
            ).from(
                entity(SharedLetterEntity::class),
                fetchJoin(SharedLetterEntity::pet),
            ).where(
                and(
                    query.startDate?.let { path(SharedLetterEntity::createdAt).greaterThan(it) },
                    query.endDate?.let { path(SharedLetterEntity::createdAt).lessThan(it) },
                    path(SharedLetterEntity::user).eq(user),
                )
            ).orderBy(
                path(SharedLetterEntity::id).asc()
            )
        }.filterNotNull()

    override fun retrieve(
        user: UserEntity?,
        query: RetrieveSharedLetterRequest
    ): List<SharedLetterEntity> =
        sharedLetterRepository.findSlice(PageRequest.of(0, query.limit)) {
            select(
                entity(SharedLetterEntity::class),
            ).from(
                entity(SharedLetterEntity::class),
                fetchJoin(SharedLetterEntity::pet),
            ).where(
                and(
                    user.let { path(SharedLetterEntity::user).notEqual(it) },
                    query.after?.let { path(LetterEntity::id).lessThan(it) },
                    query.startDate?.let { path(SharedLetterEntity::createdAt).greaterThan(it) },
                    query.endDate?.let { path(SharedLetterEntity::createdAt).lessThan(it) },
                )
            ).orderBy(
                path(SharedLetterEntity::id).asc()
            )
        }.filterNotNull()
}