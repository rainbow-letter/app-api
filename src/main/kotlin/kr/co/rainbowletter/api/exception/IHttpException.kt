package kr.co.rainbowletter.api.exception

import org.springframework.http.HttpStatus

interface IHttpException {
    fun render(): ErrorResponse
}

open class ErrorResponse(
    val status: HttpStatus,
)