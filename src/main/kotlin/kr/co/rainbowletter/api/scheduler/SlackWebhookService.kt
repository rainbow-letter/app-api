package kr.co.rainbowletter.api.scheduler

import kr.co.rainbowletter.api.client.SlackReviewClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SlackWebhookService(
    private val slackMessageFormatter: SlackMessageFormatter,
    private val slackReviewClient: SlackReviewClient
) {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun sendReportToSlack(report: LetterReportResponse): Boolean {
        val message = slackMessageFormatter.formatReport(report)
        val payload = mapOf("text" to message)

        try {
            slackReviewClient.sendSlackMessage(payload)
            logger.info("Slack 메시지 전송 성공")
        } catch (e: Exception) {
            logger.error("Slack 메시지 전송 실패: ${e.message}")
            return false
        }
        return true
    }
}