package kr.co.rainbowletter.api.file.event

import java.io.InputStream

class ImageUploadEvent(
    val inputStream: InputStream,
    val filePath: String,
    val contentType: String = "image/webp"
)