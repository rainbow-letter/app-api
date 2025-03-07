package kr.co.rainbowletter.api.pet

import kr.co.rainbowletter.api.data.entity.PetEntity
import kr.co.rainbowletter.api.data.repository.PetRepository
import org.springframework.stereotype.Service

interface IPetService {
    fun findByUserId(userId: Long): List<PetEntity>
}

@Service
class PetService(
    private val petRepository: PetRepository
) : IPetService {
    override fun findByUserId(userId: Long): List<PetEntity> {
        return petRepository.findByUserId(userId)
    }
}