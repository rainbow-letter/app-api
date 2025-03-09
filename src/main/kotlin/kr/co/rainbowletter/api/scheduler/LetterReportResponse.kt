package kr.co.rainbowletter.api.scheduler

import java.time.LocalDateTime

data class LetterReportResponse(
    val totalLetters: Long, // 총 편지 개수
    val inspectionPending: Long,  // 검수대기
    val replySent: Long,  // 발송완료
    val replyFailed: Long,  // 발송실패
    val letterStartTime: LocalDateTime,
    val letterEndTime: LocalDateTime
) {

    private fun calculatePercentage(value: Long): String {
        return if (totalLetters == 0L) "0.0%" else "%.2f%%".format(value * 100.0 / totalLetters)
    }

    val inspectionPendingPercentage: String get() = calculatePercentage(inspectionPending)
    val replySentPercentage: String get() = calculatePercentage(replySent)
    val replyFailedPercentage: String get() = calculatePercentage(replyFailed)
}