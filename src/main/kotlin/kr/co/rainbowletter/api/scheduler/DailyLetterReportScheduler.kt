package kr.co.rainbowletter.api.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DailyLetterReportScheduler(
    private val letterReportService: LetterReportService,
    private val slackWebhookService: SlackWebhookService
) {
    @Scheduled(cron = "0 10 10 * * *") // 매일 오전 10:10 실행
    fun sendDailyLetterReport() {
        val today = LocalDateTime.now()
        val startDate = today.minusDays(1).withHour(10).withMinute(0).withSecond(0)
        val endDate = today.withHour(9).withMinute(59).withSecond(59)

        val report = letterReportService.getDailyLetterReport(startDate, endDate)
        slackWebhookService.sendReportToSlack(report)
    }
}