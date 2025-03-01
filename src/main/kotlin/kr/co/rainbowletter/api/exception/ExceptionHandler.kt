package kr.co.rainbowletter.api.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException

@ControllerAdvice
class ExceptionHandler {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNotFound(): ResponseEntity<ErrorResponse> =
        ResponseEntity<ErrorResponse>(ErrorResponse(HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND)

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        // render
        val errorResponse =
            if (ex is IHttpException) ex.render()
            else ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR)

        logger.error("Exception occurred: ${ex.message}", ex)

        return ResponseEntity<ErrorResponse>(errorResponse, errorResponse.status)
    }
}