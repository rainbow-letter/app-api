package kr.co.rainbowletter.api.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNotFound(): ResponseEntity<ErrorResponse> =
        ResponseEntity<ErrorResponse>(ErrorResponse(HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND)

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        // render
        val errorResponse =
            if (ex is IHttpException) ex.render()
            else ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR)

        // TODO(로깅 추가)

        return ResponseEntity<ErrorResponse>(errorResponse, errorResponse.status)
    }
}