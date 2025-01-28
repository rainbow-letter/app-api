package kr.co.rainbowletter.api.file

import io.swagger.v3.oas.annotations.Operation
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
    private val imageService: ImageService,
    private val imageMigrateService: ImageMigrateService,
) {
    @GetMapping("temp")
    fun download() {
        imageMigrateService.run()
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @RequireAuthentication
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "이미지 업로드")
    fun upload(@RequestParam("file") file: MultipartFile): ResponseEntity<ImageCreateResponse> {
        return uploadImage(file)
    }

    @PostMapping("/profile", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @RequireAuthentication
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "프로필 이미지 업로드")
    fun uploadProfileImage(@RequestParam("file") file: MultipartFile): ResponseEntity<ImageCreateResponse> {
        return uploadImage(file, "profile")
    }

    @PostMapping("/letter", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @RequireAuthentication
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "편지 이미지 업로드")
    fun uploadLetterImage(@RequestParam("file") file: MultipartFile): ResponseEntity<ImageCreateResponse> {
        return uploadImage(file, "letter")
    }

    private fun uploadImage(file: MultipartFile, category: String? = null): ResponseEntity<ImageCreateResponse> {
        return ResponseEntity(
            ImageCreateResponse(
                imageService.uploadImage(file, category)
            ), HttpStatus.CREATED
        )
    }
}

data class ImageCreateResponse(
    val objectKey: String,
)