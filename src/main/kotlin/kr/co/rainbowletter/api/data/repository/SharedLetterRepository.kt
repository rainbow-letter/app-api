package kr.co.rainbowletter.api.data.repository

import kr.co.rainbowletter.api.data.entity.SharedLetterEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SharedLetterRepository : JpaRepository<SharedLetterEntity, Long>