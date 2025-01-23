package kr.co.rainbowletter.api.file

import org.springframework.web.multipart.MultipartFile

interface IStorageService {
    fun uploadProfileImage(file: MultipartFile): String
    fun uploadLetterImage(file: MultipartFile): String
}