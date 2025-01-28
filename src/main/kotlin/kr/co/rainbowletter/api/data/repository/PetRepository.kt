package kr.co.rainbowletter.api.data.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PetRepository : JpaRepository<kr.co.rainbowletter.api.data.entity.PetEntity, Long> {
    fun findByImageIsNotNull(): List<kr.co.rainbowletter.api.data.entity.PetEntity>
}