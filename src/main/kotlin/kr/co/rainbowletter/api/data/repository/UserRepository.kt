package kr.co.rainbowletter.api.data.repository

import kr.co.rainbowletter.api.data.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}