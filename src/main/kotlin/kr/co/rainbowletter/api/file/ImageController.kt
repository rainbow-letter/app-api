package kr.co.rainbowletter.api.file

import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.rainbowletter.api.auth.RequireAuthentication
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/images")
@Tag(name = "image")
class ImageController(
    private val storageService: IStorageService,
) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @RequireAuthentication
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestParam("file") file: MultipartFile,
    ): ResponseEntity<ImageCreateResponse> = ResponseEntity<ImageCreateResponse>(
        ImageCreateResponse(
            storageService.create(file, "uploads"),
        ),
        HttpStatus.CREATED,
    )
}

data class ImageCreateResponse(
    val objectKey: String,
)