package kr.co.rainbowletter.api.exception

import org.springframework.http.HttpStatus

class EntityNotFoundException(
    private val resourceName: String,
    private val resourceId: Any
) : Exception(), IHttpException {
    override fun render() = EntityNotFoundErrorResponse(resourceName, resourceId)
}

class EntityNotFoundErrorResponse(
    val resourceName: String,
    val resourceId: Any
) : ErrorResponse(HttpStatus.NOT_FOUND) {
    val message = "리소스를 찾을 수 없습니다."
}