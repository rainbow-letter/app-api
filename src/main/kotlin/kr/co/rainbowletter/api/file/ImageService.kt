package kr.co.rainbowletter.api.file

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.webp.WebpWriter
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

@Service
class ImageService(
    private val storageService: StorageService
) {
    fun uploadImage(file: MultipartFile, category: String): String {
        val webpData = convertToWebpWithResize(file.inputStream)
        return storageService.uploadFile(webpData, category)
    }

    private fun convertToWebpWithResize(inputStream: InputStream): ByteArray {
        return try {
            ImmutableImage.loader()
                .fromStream(inputStream)
                .max(1280, 1280)
                .bytes(WebpWriter.DEFAULT)
        } catch (e: Exception) {
            throw RuntimeException("이미지 변환 실패", e)
        }
    }
}