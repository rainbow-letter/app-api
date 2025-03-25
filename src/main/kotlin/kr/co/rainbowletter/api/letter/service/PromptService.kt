package kr.co.rainbowletter.api.letter.service

import kr.co.rainbowletter.api.data.entity.PromptEntity
import kr.co.rainbowletter.api.data.repository.PromptRepository
import kr.co.rainbowletter.api.exception.EntityNotFoundException
import org.springframework.stereotype.Service
import kotlin.reflect.jvm.jvmName

interface IPromptService {
    fun getDefault(): PromptEntity
}

@Service
class PromptService(
    private val promptRepository: PromptRepository
) : IPromptService {
    override fun getDefault(): PromptEntity =
        promptRepository.findByIsDefaultTrue() ?: throw EntityNotFoundException(PromptEntity::class.jvmName, 1)
}