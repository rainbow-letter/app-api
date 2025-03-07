package kr.co.rainbowletter.api.sharedLetter

import io.swagger.v3.oas.annotations.media.Schema

data class SharedLetterRequest(
//    @field:Schema(
//        example = "1",
//        description = "0: 내가 보낸편지, 1: 아이가 보낸편지",
//        type = "tinyint",
//        required = false
//    )
//    val recipientType: RecipientType,

    @field:Schema(
        example = "엄마를 만나\n행복했니?\n엄마는 미키 덕분에\n정말 행복했어",
        description = "편지 본문",
        maxLength = 56,
        required = false
    )
    val content: String,
)