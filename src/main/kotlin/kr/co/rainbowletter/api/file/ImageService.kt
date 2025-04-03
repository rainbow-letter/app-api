package kr.co.rainbowletter.api.file

import kr.co.rainbowletter.api.file.event.ImageUploadEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class ImageService(
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun uploadImage(file: MultipartFile, category: String? = null): String {
        val filePath = generateFilePath(category)
        applicationEventPublisher.publishEvent(
            ImageUploadEvent(file.inputStream, filePath)
        )
        return filePath
    }

    private fun generateFilePath(category: String?): String {
        val fileName = UUID.randomUUID().toString().replace("-", "").substring(0, 16)
        val datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        return category?.let { "$it/$datePath/$fileName.webp" } ?: "$datePath/$fileName.webp"
    }
}