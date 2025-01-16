package kr.co.rainbowletter.api.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(Exception::class)
    protected fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        // render
        val errorResponse =
            if (ex is IHttpException) ex.render()
            else ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR)

        return ResponseEntity<ErrorResponse>(errorResponse, errorResponse.status)
    }
}