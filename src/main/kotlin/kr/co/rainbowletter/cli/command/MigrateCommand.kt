package kr.co.rainbowletter.cli.command

import kr.co.rainbowletter.data.entity.has.HasImage
import kr.co.rainbowletter.data.repository.LetterRepository
import kr.co.rainbowletter.data.repository.PetRepository
import kr.co.rainbowletter.data.repository.actions.IImageRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import picocli.CommandLine


@CommandLine.Command(name = "migrate", description = ["이미지 이관 도구"])
@Component
class MigrateCommand(
    private val petRepository: PetRepository,
    private val letterRepository: LetterRepository,
) : Runnable {
    override fun run() {
        listOf(petRepository, letterRepository).forEach { repository ->
            copyImage(repository)
        }
    }

    fun <T : HasImage, V> copyImage(repository: IImageRepository<T, V>) {
        val rows = repository.findByImageNotContains("s3", PageRequest.of(0, 100))

        rows.forEach { row ->
            row.image = row.image?.let { migrate(it) } ?: row.image
        }

        repository.saveAll(rows)
    }

    fun migrate(path: String): String {
//        https://rainbowletter.s3.ap-northeast-2.amazonaws.com/index.html
        return path
    }
}