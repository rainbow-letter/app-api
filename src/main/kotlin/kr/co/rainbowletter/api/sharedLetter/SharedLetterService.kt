package kr.co.rainbowletter.api.sharedLetter

import kr.co.rainbowletter.api.data.entity.HasOwnerExtension.Companion.throwIfDenied
import kr.co.rainbowletter.api.data.entity.SharedLetterEntity
import kr.co.rainbowletter.api.data.entity.UserEntity
import kr.co.rainbowletter.api.data.repository.PetRepository
import kr.co.rainbowletter.api.data.repository.RepositoryExtension.Companion.findByIdOrThrow
import kr.co.rainbowletter.api.data.repository.SharedLetterRepository
import kr.co.rainbowletter.api.data.repository.SharedLetterRepositoryExtension.Companion.getTodayWriteByPetId
import kr.co.rainbowletter.api.data.repository.SharedLetterRepositoryExtension.Companion.retrieve
import kr.co.rainbowletter.api.exception.SharedLetterDuplicatedException
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

    fun delete(letterId: Long)
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
        if (sharedLetterRepository.getTodayWriteByPetId(petId).isNotEmpty()) {
            throw SharedLetterDuplicatedException()
        }

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
    ) = sharedLetterRepository.retrieve(user, query.startDate, query.endDate)

    override fun retrieve(
        user: UserEntity?,
        query: RetrieveSharedLetterRequest
    ): List<SharedLetterEntity> = sharedLetterRepository.retrieve(
        user, query.after, query.startDate, query.endDate, query.ids, query.limit, query.randomSort
    )

    override fun delete(letterId: Long) {
        val letter = sharedLetterRepository.findByIdOrThrow(letterId)
        sharedLetterRepository.delete(letter)
    }
}