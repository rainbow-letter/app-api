package kr.co.rainbowletter.api.data.repository

import kr.co.rainbowletter.api.data.entity.ReplyEntity
import kr.co.rainbowletter.api.data.entity.ReplyStatus
import org.springframework.data.jpa.repository.JpaRepository

interface ReplyRepository : JpaRepository<ReplyEntity, Long> {
    fun countByInspectionFalseAndLetterIdIn(letterIds: List<Long>): Long
    fun countByStatusAndLetterIdIn(status: ReplyStatus, letterIds: List<Long>): Long
}