package kr.co.rainbowletter.api.file

import com.sksamuel.scrimage.ImageParseException
import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.webp.WebpWriter
import kr.co.rainbowletter.api.exception.ImageUploadException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

@Service
class ImageService(
    private val storageService: StorageService,
) {
    fun uploadImage(file: MultipartFile, category: String? = null): String {
        val webpData = convertToWebpWithResize(file.inputStream)
        return storageService.uploadFile(webpData, "image/webp", "webp", category)
    }

    private fun convertToWebpWithResize(inputStream: InputStream): ByteArray {
        return try {
            ImmutableImage
                .loader()
                .fromStream(inputStream)
                .bytes(WebpWriter.DEFAULT)
        } catch (e: ImageParseException) {
            throw throw ImageUploadException("지원하지 않는 이미지 형식입니다.", e)
        } catch (e: Throwable) {
            throw RuntimeException("이미지 변환 실패", e)
        }
    }
}