package kr.co.rainbowletter.data.repository

import kr.co.rainbowletter.data.entity.LetterEntity
import kr.co.rainbowletter.data.repository.actions.IImageRepository
import org.springframework.data.jpa.repository.JpaRepository

interface LetterRepository : JpaRepository<LetterEntity, Long>, IImageRepository<LetterEntity, Long>