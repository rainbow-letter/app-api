package kr.co.rainbowletter.api.data.repository

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import kr.co.rainbowletter.api.data.entity.LetterEntity
import kr.co.rainbowletter.api.data.entity.PetEntity
import kr.co.rainbowletter.api.data.entity.UserEntity
import kr.co.rainbowletter.api.letter.RetrieveLetterRequest

class JpqlExtension : Jpql() {
    fun letterRetrieve(
        query: RetrieveLetterRequest,   // 사용자가 이미 정의한 쿼리 객체
        petId: Long?,
        userId: Long?
    ): Predicate {
        return and(
            query.after?.let { path(LetterEntity::id).lessThan(it) },
            query.startDate?.let { path(LetterEntity::createdAt).greaterThan(it) },
            query.endDate?.let { path(LetterEntity::createdAt).lessThan(it) },
            petId?.let { path(LetterEntity::pet)(PetEntity::id).eq(petId) },
            userId?.let { path(LetterEntity::user)(UserEntity::id).eq(userId) },
        )
    }
}