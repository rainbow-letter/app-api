package kr.co.rainbowletter.api.data.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.NumberExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import kr.co.rainbowletter.api.data.entity.QLetterEntity
import kr.co.rainbowletter.api.data.entity.QReplyEntity
import kr.co.rainbowletter.api.data.entity.ReplyStatus
import kr.co.rainbowletter.api.data.repository.dto.LetterStats
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class LetterRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) {

    fun getLetterReportByCreatedAtBetween(
        start: LocalDateTime,
        end: LocalDateTime
    ): LetterStats {
        val letter = QLetterEntity.letterEntity
        val reply = QReplyEntity.replyEntity

        return queryFactory
            .select(
                Projections.constructor(
                    LetterStats::class.java,
                    letter.id.countDistinct(),
                    countByCondition(reply.inspection.eq(false)),
                    countByCondition(reply.status.eq(ReplyStatus.REPLY)),
                    countByCondition(reply.status.eq(ReplyStatus.CHAT_GPT))
                )
            )
            .from(letter)
            .leftJoin(reply).on(letter.eq(reply.letter))
            .where(
                letter.createdAt.goe(start),
                letter.createdAt.lt(end)
            )
            .fetchOne() ?: LetterStats(0L, 0L, 0L, 0L)
    }

    private fun countByCondition(condition: BooleanExpression): NumberExpression<Long> {
        return Expressions.cases()
            .`when`(condition).then(1L)
            .otherwise(0L).sum()
    }
}