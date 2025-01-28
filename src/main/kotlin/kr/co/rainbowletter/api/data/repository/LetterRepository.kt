package kr.co.rainbowletter.api.data.repository

import org.springframework.data.jpa.repository.JpaRepository

interface LetterRepository : JpaRepository<kr.co.rainbowletter.api.data.entity.LetterEntity, Long> {
    fun findByImageIsNotNull(): List<kr.co.rainbowletter.api.data.entity.LetterEntity>
}