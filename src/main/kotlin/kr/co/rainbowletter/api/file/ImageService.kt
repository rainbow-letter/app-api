package kr.co.rainbowletter.api.file

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.webp.WebpWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.rainbowletter.api.slack.SlackErrorReportService
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class ImageService(
    private val storageService: StorageService,
    private val slackErrorReportService: SlackErrorReportService
) {
    private val logger = KotlinLogging.logger {}

    fun uploadImage(file: MultipartFile, category: String? = null): String {
        val filePath = generateFilePath(category)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val webpData = convertToWebpWithResize(file.inputStream)
                storageService.uploadFile(webpData, "image/webp", filePath)
            } catch (e: Exception) {
                logger.error(e) { "[이미지 업로드 실패] $filePath" }
                slackErrorReportService.sendErrorReportToSlack(filePath, e)
                // TODO: 자동 재시도 로직 추가
            }
        }
        return filePath
    }

    private fun generateFilePath(category: String?): String {
        val fileName = UUID.randomUUID().toString().replace("-", "").substring(0, 16)
        val datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        return category?.let { "$it/$datePath/$fileName.webp" } ?: "$datePath/$fileName.webp"
    }

    private fun convertToWebpWithResize(inputStream: InputStream): ByteArray {
        return ImmutableImage.loader()
            .fromStream(inputStream)
            .max(1280, 1280)
            .bytes(WebpWriter.DEFAULT)
    }
}