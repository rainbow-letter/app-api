package kr.co.rainbowletter.api.data.repository.dto

data class LetterStats(
    val totalLetters: Long = 0L,
    val inspectionPending: Long = 0L,
    val replySent: Long = 0L,
    val replyFailed: Long = 0L
)