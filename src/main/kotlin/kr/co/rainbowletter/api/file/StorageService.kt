package kr.co.rainbowletter.api.file

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ObjectCannedACL
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.model.S3Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class StorageService(
    private val s3Client: S3Client,
    @Value("\${cloud.aws.s3.bucket}") private val bucket: String
) {
    private val log = KotlinLogging.logger {}

    fun uploadFile(data: ByteArray, category: String): String {
        val filePath = getFilePath(category)
        try {
            s3Client.putObject(
                PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(filePath)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .contentType("image/webp")
                    .build(),
                RequestBody.fromBytes(data)
            )
        } catch (e: S3Exception) {
            log.error("S3 업로드 실패: 버킷=$bucket, 경로=$filePath", e)
            throw RuntimeException("S3 업로드 실패: ${e.message}", e)
        } catch (e: Exception) {
            log.error("알 수 없는 오류로 S3 업로드 실패: 경로=$filePath", e)
            throw RuntimeException("파일 업로드 중 알 수 없는 오류가 발생했습니다.", e)
        }
        return filePath
    }

    private fun getFilePath(category: String): String {
        val datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val fileName = UUID.randomUUID().toString().replace("-", "").substring(0, 16)
        return "$category/$datePath/$fileName.webp"
    }
}
