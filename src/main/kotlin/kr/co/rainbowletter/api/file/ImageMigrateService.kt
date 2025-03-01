package kr.co.rainbowletter.api.file

import kr.co.rainbowletter.api.data.entity.has.HasImage
import kr.co.rainbowletter.api.data.repository.LetterRepository
import kr.co.rainbowletter.api.data.repository.PetRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

@Service
class ImageMigrateService(
    private val petRepository: PetRepository,
    private val letterRepository: LetterRepository,
    private val imageService: ImageService,
) {
    private val webClient: WebClient = WebClient.builder()
        .codecs { codecs ->
            codecs.defaultCodecs().maxInMemorySize(1024 * 1024 * 1024) // 메모리 제한을 10MB로 설정
        }
        .build()

    fun run() {
        loadImages(petRepository.findByImageIsNotNull())
        loadImages(letterRepository.findByImageIsNotNull())
    }

    private fun loadImages(entities: List<HasImage>) {
        val counter = AtomicInteger(0)

        val a = Flux.fromIterable(entities)
            .parallel(4)
            .flatMap({ row ->
                loadImage(row.image!!, counter.incrementAndGet())
            }, false, 4)
            .doOnNext {
                println("작업 완료됨: ${Thread.currentThread().name}")
            }
            .sequential()
            .blockLast()
    }

    private fun loadImage(path: String, i: Int): Mono<Void> {
        val file = File("/Users/jipark/Workspace/temp/rainbowletter-images/$path")

        if (file.exists()) {
            println("[skip] id: $i, path: $path")
            return Mono.empty()
        }

        file.parentFile?.apply {
            if (!exists()) mkdirs()
        }

        return webClient.get()
            .uri("https://rainbowletter.co.kr/api/images/resources/$path")
            .retrieve()
            .bodyToMono(ByteArray::class.java)
            .map { o -> tryImageResize(o!!, i) }
            .doOnNext { o -> file.writeBytes(o) }
            .doOnNext { _ -> println("[end] id: $i, path: $path") }
            .then()
    }

    private fun tryImageResize(input: ByteArray, i: Int) = try {
        imageService.convertToWebpWithResize(input)
    } catch (e: Exception) {
        println(i)
        println(e)
        input
    }
}