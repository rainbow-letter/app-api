package kr.co.rainbowletter.api.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DailyLetterReportScheduler(
    private val letterReportService: LetterReportService,
    private val slackWebhookService: SlackWebhookService
) {
    @Scheduled(cron = "0 10 10 * * *") // 매일 오전 10:10 실행
    fun sendDailyLetterReport() {
        val report = letterReportService.getDailyLetterReport()
        slackWebhookService.sendReportToSlack(report)
    }
}