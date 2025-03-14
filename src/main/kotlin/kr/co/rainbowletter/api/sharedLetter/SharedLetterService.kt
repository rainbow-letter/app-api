package kr.co.rainbowletter.api.sharedLetter

import kr.co.rainbowletter.api.data.entity.HasOwnerExtension.Companion.throwIfDenied
import kr.co.rainbowletter.api.data.entity.SharedLetterEntity
import kr.co.rainbowletter.api.data.entity.UserEntity
import kr.co.rainbowletter.api.data.repository.PetRepository
import kr.co.rainbowletter.api.data.repository.RepositoryExtension.Companion.findByIdOrThrow
import kr.co.rainbowletter.api.data.repository.SharedLetterRepository
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
}