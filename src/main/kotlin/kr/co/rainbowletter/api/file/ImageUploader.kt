package kr.co.rainbowletter.api.file

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.webp.WebpWriter
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ObjectCannedACL
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.InputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class ImageUploader(
    private val s3Client: S3Client,
    @Value("\${cloud.aws.s3.bucket}") private val bucket: String,
    @Value("\${cloud.aws.cloudfront.url}") private val cloudFrontUrl: String
) {
    private val log = KotlinLogging.logger {}

    companion object {
        const val DATE_FORMAT_YYYYMMDD: String = "yyyy/MM/dd"
    }

    fun uploadImage(file: MultipartFile, category: String): String {
        val filePath = getFilePath(category)
        try {
            // InputStream에서 직접 변환된 WebP 데이터를 S3에 업로드
            file.inputStream.use { inputStream ->
                val webpData = convertToWebpWithResize(inputStream)
                s3Client.putObject(
                    PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(filePath)
                        .acl(ObjectCannedACL.PUBLIC_READ)
                        .contentType("image/webp")
                        .build(),
                    RequestBody.fromBytes(webpData)
                )
            }
        } catch (e: Exception) {
            log.error("이미지 업로드 실패", e)
            throw RuntimeException(e.message, e)
        }

        return filePath
    }

    fun convertToWebpWithResize(inputStream: InputStream): ByteArray {
        return try {
            // InputStream에서 이미지를 읽어와 WebP로 변환 후 ByteArray로 반환
            ImmutableImage.loader()
                .fromStream(inputStream) // InputStream으로부터 이미지 로드
                .max(1280, 1280)
                .bytes(WebpWriter.DEFAULT)
        } catch (e: Exception) {
            log.error("Webp 변환 실패", e)
            throw RuntimeException(e.message, e)
        }
    }

    private fun getFilePath(category: String): String {
        return "$category/${createDatePath()}/${generateRandomFileNamePrefix()}.webp"
    }

    private fun createDatePath(): String {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_YYYYMMDD))
    }

    private fun generateRandomFileNamePrefix(): String {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16)
    }

}