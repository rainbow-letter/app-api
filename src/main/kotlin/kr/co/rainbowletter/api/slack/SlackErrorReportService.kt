package kr.co.rainbowletter.api.slack

import kr.co.rainbowletter.api.client.SlackErrorClient
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory

@Service
class SlackErrorReportService(
    private val slackErrorClient: SlackErrorClient
) {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun sendErrorReportToSlack(filePath: String, exception: Throwable) {
        val message = """
            ❌ *이미지 업로드 실패*
            - 경로: `$filePath`
            - 사유: ${exception.message ?: "알 수 없는 에러"}
        """.trimIndent()

        try {
            slackErrorClient.sendSlackMessage(mapOf("text" to message))
            logger.info("슬랙 에러 메시지 전송 성공")
        } catch (e: Exception) {
            logger.error("슬랙 에러 메시지 전송 실패: ${e.message ?: "알 수 없는 에러"}")
        }
    }
}