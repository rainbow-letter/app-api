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
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class ImageUploader(
    private val s3Client: S3Client
) {
    @Value("\${cloud.aws.s3.bucket}")
    lateinit var bucket: String

    @Value("\${cloud.aws.cloudfront.url}")
    lateinit var cloudFrontUrl: String

    private val log = KotlinLogging.logger {}

    companion object {
        const val DATE_FORMAT_YYYYMMDD: String = "yyyy/MM/dd"
    }

    fun uploadImage(file: MultipartFile, category: String): String {
        val filePath = getFilePath(category)
        var convertedFile: File? = null // WebP로 변환된 파일
        var originalFile: File? = null // MultipartFile을 변환한 원본 파일

        try {
            originalFile = convertMultipartToFile(file) // 원본 파일 변환
            convertedFile = convertToWebpWithResize(originalFile, filePath) // WebP 변환

            FileInputStream(convertedFile).use { fileInputStream ->
                s3Client.putObject(
                    PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(filePath)
                        .acl(ObjectCannedACL.PUBLIC_READ)
                        .contentType("image/webp")
                        .build(),
                    RequestBody.fromInputStream(fileInputStream, convertedFile.length())
                )
            }
        } catch (e: Exception) {
            log.error("이미지 업로드 실패", e)
            throw RuntimeException(e.message, e)
        } finally {
            deleteLocalFile(originalFile) // 원본 파일 삭제
            deleteLocalFile(convertedFile) // 변환된 파일 삭제
        }
        return getCloudFrontUrl(filePath)
    }

    fun convertToWebpWithResize(originalFile: File, fileName: String): File {
        return try {
            val webpFile = File("$fileName.webp")
            Files.createDirectories(webpFile.parentFile.toPath())

            ImmutableImage.loader()
                .fromFile(originalFile)
                .max(1280, 1280)
                .output(WebpWriter.DEFAULT, webpFile)

            webpFile
        } catch (e: Exception) {
            log.error("Webp 변환 실패", e)
            throw RuntimeException(e.message, e)
        }
    }

    private fun convertMultipartToFile(file: MultipartFile): File {
        val convFile = File("${System.getProperty("java.io.tmpdir")}/${file.originalFilename}")
        file.transferTo(convFile)
        return convFile
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

    private fun getCloudFrontUrl(fileKey: String): String {
        return "$cloudFrontUrl/$fileKey"
    }

    private fun deleteLocalFile(file: File?) {
        if (file?.exists() == true && !file.delete()) {
            log.warn { "로컬 파일 삭제 실패: ${file.absolutePath}" }
        }
    }

}