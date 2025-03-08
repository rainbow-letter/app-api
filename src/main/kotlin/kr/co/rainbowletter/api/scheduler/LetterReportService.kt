package kr.co.rainbowletter.api.scheduler

import kr.co.rainbowletter.api.data.repository.LetterRepositoryImpl
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LetterReportService(
    private val letterRepositoryImpl: LetterRepositoryImpl
) {
    fun getDailyLetterReport(): LetterReport {
        val letterStartTime = LocalDateTime.now().minusDays(1).withHour(10).withMinute(0).withSecond(0)
        val letterEndTime = LocalDateTime.now().withHour(9).withMinute(59).withSecond(59)

        val letterStats = letterRepositoryImpl.getLetterReportByCreatedAtBetween(letterStartTime, letterEndTime)

        println(letterStats)
        println(letterStartTime)
        println(letterEndTime)

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