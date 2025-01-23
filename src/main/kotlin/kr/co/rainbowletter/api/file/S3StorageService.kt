package kr.co.rainbowletter.api.file

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class S3StorageService(
    private val imageUploader: ImageUploader
) : IStorageService {

    companion object {
        const val CATEGORY_PROFILE: String = "profile"
        const val CATEGORY_LETTER: String = "letter"
    }

    override fun uploadProfileImage(file: MultipartFile): String {
        return imageUploader.uploadImage(file, CATEGORY_PROFILE)
    }

    override fun uploadLetterImage(file: MultipartFile): String {
        return imageUploader.uploadImage(file, CATEGORY_LETTER)
    }
}