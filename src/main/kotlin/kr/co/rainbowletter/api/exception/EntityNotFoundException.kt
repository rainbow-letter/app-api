package kr.co.rainbowletter.api.exception

import org.springframework.http.HttpStatus

class EntityNotFoundException : Exception(), IHttpException {
    override fun render() = EntityNotFoundErrorResponse()
}

class EntityNotFoundErrorResponse : ErrorResponse(HttpStatus.NOT_FOUND) {
    val message = "리소스를 찾을 수 없습니다."
}