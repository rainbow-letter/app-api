package kr.co.rainbowletter.api.exception

import org.springframework.http.HttpStatus

class AuthenticationException : Exception(), IHttpException {
    override fun render() = AuthenticationErrorResponse()
}

class AuthenticationErrorResponse : ErrorResponse(HttpStatus.UNAUTHORIZED) {
    val message = "인증에 실패했습니다"
}