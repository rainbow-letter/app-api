package kr.co.rainbowletter.data.repository

import kr.co.rainbowletter.data.entity.PetEntity
import kr.co.rainbowletter.data.repository.actions.IImageRepository
import org.springframework.data.jpa.repository.JpaRepository

interface PetRepository : JpaRepository<PetEntity, Long>, IImageRepository<PetEntity, Long>