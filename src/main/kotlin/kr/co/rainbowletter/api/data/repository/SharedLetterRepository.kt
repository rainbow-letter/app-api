package kr.co.rainbowletter.api.data.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import kr.co.rainbowletter.api.data.entity.PetEntity
import kr.co.rainbowletter.api.data.entity.SharedLetterEntity
import kr.co.rainbowletter.api.data.entity.UserEntity
import kr.co.rainbowletter.api.util.extension.startOfDay
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface SharedLetterRepository : JpaRepository<SharedLetterEntity, Long>, KotlinJdslJpqlExecutor

interface SharedLetterRepositoryExtension {
    companion object {
        fun SharedLetterRepository.getTodayWriteByPetId(petId: Long) = this.findAll {
            select(
                entity(SharedLetterEntity::class)
            ).from(
                entity(SharedLetterEntity::class)
            ).where(
                and(
                    path(SharedLetterEntity::pet)(PetEntity::id).eq(petId),
                    path(SharedLetterEntity::createdAt).greaterThan(LocalDateTime.now().startOfDay()),
                )
            )
        }.filterNotNull()

        fun SharedLetterRepository.retrieve(
            user: UserEntity,
            startDate: LocalDateTime? = null,
            endDate: LocalDateTime? = null,
        ): List<SharedLetterEntity> = this.findAll {
            select(
                entity(SharedLetterEntity::class),
            ).from(
                entity(SharedLetterEntity::class),
                fetchJoin(SharedLetterEntity::pet),
            ).where(
                and(
                    startDate?.let { path(SharedLetterEntity::createdAt).greaterThan(it) },
                    endDate?.let { path(SharedLetterEntity::createdAt).lessThan(it) },
                    path(SharedLetterEntity::user).eq(user),
                )
            ).orderBy(
                path(SharedLetterEntity::id).asc()
            )
        }.filterNotNull()

        fun SharedLetterRepository.retrieve(
            user: UserEntity?,
            after: Long?,
            startDate: LocalDateTime?,
            endDate: LocalDateTime?,
            ids: List<Long>?,
            limit: Int,
            isRandomSort: Boolean,
        ) = this.findSlice(PageRequest.of(0, limit)) {
            select(
                entity(SharedLetterEntity::class),
            ).from(
                entity(SharedLetterEntity::class),
                fetchJoin(SharedLetterEntity::pet),
            ).where(
                and(
                    ids?.let { path(SharedLetterEntity::id).`in`(it) },
                    user.let { path(SharedLetterEntity::user).notEqual(it) },
                    after?.let { path(SharedLetterEntity::id).greaterThan(it) },
                    startDate?.let { path(SharedLetterEntity::createdAt).greaterThan(it) },
                    endDate?.let { path(SharedLetterEntity::createdAt).lessThan(it) },
                )
            ).orderBy(
                if (isRandomSort)
                    customExpression(String::class, "rand()").asc()
                else
                    path(SharedLetterEntity::id).asc()
            )
        }.filterNotNull()
    }
}