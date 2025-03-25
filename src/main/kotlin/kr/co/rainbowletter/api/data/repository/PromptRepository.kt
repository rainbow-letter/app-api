package kr.co.rainbowletter.api.data.repository

import kr.co.rainbowletter.api.data.entity.PromptEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PromptRepository : JpaRepository<PromptEntity, Long> {
    fun findByIsDefaultTrue(): PromptEntity?
}