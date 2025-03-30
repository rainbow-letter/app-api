package kr.co.rainbowletter.api.data.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import kr.co.rainbowletter.api.data.entity.LetterEntity
import kr.co.rainbowletter.api.data.entity.PetEntity
import kr.co.rainbowletter.api.data.entity.UserEntity
import kr.co.rainbowletter.api.letter.PetSequence
import kr.co.rainbowletter.api.letter.RetrieveLetterRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface LetterRepository : JpaRepository<LetterEntity, Long>, KotlinJdslJpqlExecutor {
    fun user(user: UserEntity): MutableList<LetterEntity>
}

interface LetterRepositoryExtension {
    companion object {
        fun LetterRepository.retrieve(
            query: RetrieveLetterRequest,
            petId: Long?,
            userId: Long?,
        ) = this.findSlice(PageRequest.of(0, query.limit)) {
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

        fun LetterRepository.getLastCount(
            after: Long?,
            endDate: LocalDateTime?,
            petId: Long?,
            userId: Long?,
        ) = this.findAll {
            jpql(JpqlExtension()) {
                select<PetSequence>(
                    path(LetterEntity::pet)(PetEntity::id),
                    count(LetterEntity::id)
                ).from(
                    entity(LetterEntity::class),
                ).where(
                    and(
                        after?.let { path(LetterEntity::id).lessThan(it) },
                        endDate?.let { path(LetterEntity::createdAt).lessThan(it) },
                        petId?.let { path(LetterEntity::pet)(PetEntity::id).eq(petId) },
                        userId?.let { path(LetterEntity::user)(UserEntity::id).eq(userId) },
                    )
                ).groupBy(
                    path(LetterEntity::pet)(PetEntity::id),
                )
            }
        }
    }
}
