package kr.co.rainbowletter.api.scheduler

import kr.co.rainbowletter.api.data.entity.ReplyStatus
import kr.co.rainbowletter.api.data.repository.LetterRepository
import kr.co.rainbowletter.api.data.repository.ReplyRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LetterReportService(
    private val letterRepository: LetterRepository,
    private val replyRepository: ReplyRepository
) {
    fun getDailyLetterReport(): LetterReport {
        val letterStartTime = LocalDateTime.now().minusDays(1).withHour(10).withMinute(0).withSecond(0)
        val letterEndTime = LocalDateTime.now().withHour(9).withMinute(59).withSecond(59)
        val letterIds = letterRepository.findLetterIdsByCreatedAtBetween(letterStartTime, letterEndTime)

        val totalLetters = letterIds.size.toLong()
        val inspectionPending = replyRepository.countByInspectionFalseAndLetterIdIn(letterIds)
        val replySent = replyRepository.countByStatusAndLetterIdIn(ReplyStatus.REPLY, letterIds)
        val replyFailed = replyRepository.countByStatusAndLetterIdIn(ReplyStatus.CHAT_GPT, letterIds)

        return LetterReport(totalLetters, inspectionPending, replySent, replyFailed, letterStartTime, letterEndTime)
    }
}