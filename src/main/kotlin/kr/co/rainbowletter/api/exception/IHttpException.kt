package kr.co.rainbowletter.api.exception

import kr.co.rainbowletter.api.RainbowletterApiApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus

interface IHttpException {
    fun render(): ErrorResponse
}

open class ErrorResponse(
    val status: HttpStatus,
)

fun main(args: Array<String>) {
    runApplication<RainbowletterApiApplication>(*args)
}
