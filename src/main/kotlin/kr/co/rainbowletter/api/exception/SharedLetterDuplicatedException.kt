package kr.co.rainbowletter.api.exception

import org.springframework.http.HttpStatus

class SharedLetterDuplicatedException : Exception(), IHttpException {
    override fun render() = SharedLetterDuplicatedErrorResponse()
}

class SharedLetterDuplicatedErrorResponse : ErrorResponse(HttpStatus.UNAUTHORIZED) {
    val message = "오늘 이미 작성된 편지가 있습니다."
}