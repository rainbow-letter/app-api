package kr.co.rainbowletter.api.scheduler

import kr.co.rainbowletter.api.data.repository.LetterRepositoryImpl
import kr.co.rainbowletter.api.slack.SlackReviewReportService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LetterReportService(
    private val letterRepositoryImpl: LetterRepositoryImpl,
    private val slackReviewReportService: SlackReviewReportService
) {
    fun report(
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null
    ): LetterReportResponse {
        val now = LocalDateTime.now()
        val letterStartTime = startDate ?: now.minusDays(1).withHour(10).withMinute(0).withSecond(0)
        val letterEndTime = endDate ?: now.withHour(9).withMinute(59).withSecond(59)

        val letterStats = letterRepositoryImpl.getLetterReportByCreatedAtBetween(letterStartTime, letterEndTime)

        val report = LetterReportResponse(
            letterStats.totalLetters,
            letterStats.inspectionPending,
            letterStats.replySent,
            letterStats.replyFailed,
            letterStartTime,
            letterEndTime
        )

        slackReviewReportService.sendReportToSlack(report)

        return report
    }
}