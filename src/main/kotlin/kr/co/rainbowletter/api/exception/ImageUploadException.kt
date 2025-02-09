package kr.co.rainbowletter.api.exception

import org.springframework.http.HttpStatus

class ImageUploadException(private val messageDetail: String, innerException: Throwable) : Exception(innerException),
    IHttpException {
    override fun render() = ImageUploadErrorResponse(messageDetail)
}

class ImageUploadErrorResponse(var messageDetail: String) : ErrorResponse(HttpStatus.BAD_REQUEST) {
    val message = "이미지 업로드를 실패했습니다."
}