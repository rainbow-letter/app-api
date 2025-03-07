package kr.co.rainbowletter.api.data.repository

import kr.co.rainbowletter.api.data.entity.PetEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PetRepository : JpaRepository<PetEntity, Long> {
    fun findByUserId(userId: Long): List<PetEntity>
}