package kr.co.rainbowletter.api.extensions

import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile

class FileExtension {
    companion object {
        fun MultipartFile.getExtension(): String? {
            return StringUtils.getFilenameExtension(this.originalFilename)
        }
    }
}