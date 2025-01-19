package kr.co.rainbowletter.api.file

import kr.co.rainbowletter.api.extensions.FileExtension.Companion.getExtension
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.lang.String.format
import java.util.*

@Service
class S3StorageService(
    private val s3Client: S3Client,
    @Value("\${cloud.aws.s3.bucket}") private val bucketName: String,
) : IStorageService {
    private fun getFileName(file: MultipartFile, path: String?): String {
        val fileName = StringBuilder()

        if (path != null) fileName.append("$path/")

        fileName.append(format("%s.%s", UUID.randomUUID(), file.getExtension()))
        return fileName.toString()
    }

    override fun create(file: MultipartFile, path: String?): String {
        val fileName = getFileName(file, path)

        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build(),
            software.amazon.awssdk.core.sync.RequestBody.fromInputStream(file.inputStream, file.size)
        )

        return fileName
    }

    override fun delete(path: String, prefix: String?) {
        val fileName = prefix?.let { "$prefix/$path" } ?: path

        s3Client.deleteObject(
            DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build()
        )
    }
}