package kr.co.rainbowletter.api.scheduler

import kr.co.rainbowletter.api.data.repository.LetterRepositoryImpl
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LetterReportService(
    private val letterRepositoryImpl: LetterRepositoryImpl
) {
    fun getDailyLetterReport(startDate: LocalDateTime? = null, endDate: LocalDateTime? = null): LetterReport {
        val now = LocalDateTime.now()
        val letterStartTime = startDate ?: now.minusDays(1).withHour(10).withMinute(0).withSecond(0)
        val letterEndTime = endDate ?: now.withHour(9).withMinute(59).withSecond(59)

        val letterStats = letterRepositoryImpl.getLetterReportByCreatedAtBetween(letterStartTime, letterEndTime)

        return LetterReport(
            letterStats.totalLetters,
            letterStats.inspectionPending,
            letterStats.replySent,
            letterStats.replyFailed,
            letterStartTime,
            letterEndTime
        )
    }
}