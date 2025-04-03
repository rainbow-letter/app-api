package kr.co.rainbowletter.api.file.handler

import com.sksamuel.scrimage.ImageParseException
import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.webp.WebpWriter
import kr.co.rainbowletter.api.exception.ImageUploadException
import kr.co.rainbowletter.api.file.StorageService
import kr.co.rainbowletter.api.file.event.ImageUploadEvent
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class ImageUploadEventHandler(
    private val storageService: StorageService
) {
    private val logger = KotlinLogging.logger {}

    @Async
    @EventListener
    fun handle(event: ImageUploadEvent) {
        try {
            val webpData = convertToWebpWithResize(event.inputStream)
            storageService.uploadFile(webpData, event.contentType, event.filePath)
            logger.info { "[이미지 업로드 완료] ${event.filePath}" }
        } catch (e: Exception) {
            logger.error(e) { "[이미지 업로드 실패] ${event.filePath}" }
        }
    }

    private fun convertToWebpWithResize(inputStream: InputStream): ByteArray {
        return try {
            ImmutableImage
                .loader()
                .fromStream(inputStream)
                .max(1280, 1280)
                .bytes(WebpWriter.DEFAULT)
        } catch (e: ImageParseException) {
            throw ImageUploadException("지원하지 않는 이미지 형식입니다.", e)
        } catch (e: Throwable) {
            throw RuntimeException("이미지 변환 실패", e)
        }
    }
}