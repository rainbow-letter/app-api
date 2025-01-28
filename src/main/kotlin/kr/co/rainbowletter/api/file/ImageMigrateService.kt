package kr.co.rainbowletter.api.file

import kr.co.rainbowletter.api.data.entity.has.HasImage
import kr.co.rainbowletter.api.data.repository.LetterRepository
import kr.co.rainbowletter.api.data.repository.PetRepository
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class ImageMigrateService(
    private val petRepository: PetRepository,
    private val letterRepository: LetterRepository,
) {
    private val restClient: RestClient = RestClient.builder().build()

    fun run() {
        loadImages(petRepository.findByImageIsNotNull())
        loadImages(letterRepository.findByImageIsNotNull())
    }

    private fun loadImages(entities: List<HasImage>) {
        entities.forEach { row -> loadImage(row.image!!) }
    }

    private fun loadImage(path: String) {

        val response = restClient.get()
            .uri("https://rainbowletter.co.kr/api/images/resource/$path")
            .retrieve()
            .body(ByteArray::class.java)
    }
}