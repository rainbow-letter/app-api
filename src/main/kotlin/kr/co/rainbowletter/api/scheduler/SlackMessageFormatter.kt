package kr.co.rainbowletter.api.scheduler

import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

@Component
class SlackMessageFormatter {
    fun formatReport(report: LetterReportResponse): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        return """
            📢 *Daily Letter Report*
            1. 기간: ${report.letterStartTime.format(formatter)} ~ ${report.letterEndTime.format(formatter)}
            2. 총 편지 개수: ${report.totalLetters}
            3. 검수대기: ${report.inspectionPending} (${report.inspectionPendingPercentage})
            4. 발송완료: ${report.replySent} (${report.replySentPercentage})
            5. 발송실패: ${report.replyFailed} (${report.replyFailedPercentage})
        """.trimIndent()
    }
}