package kr.co.rainbowletter.api.data.repository

import kr.co.rainbowletter.api.data.entity.LetterEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface LetterRepository : JpaRepository<LetterEntity, Long> {
    @Query("SELECT l.id FROM LetterEntity l WHERE l.createdAt >= :start AND l.createdAt < :end")
    fun findLetterIdsByCreatedAtBetween(start: LocalDateTime, end: LocalDateTime): List<Long>
}