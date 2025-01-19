package kr.co.rainbowletter.api.file

import org.springframework.web.multipart.MultipartFile

interface IStorageService {
    fun create(file: MultipartFile, path: String? = null): String
    fun delete(path: String, prefix: String? = null)
}