package kr.co.rainbowletter.api.auth.service

import kr.co.rainbowletter.api.data.entity.UserEntity
import kr.co.rainbowletter.api.data.entity.UserRepository
import kr.co.rainbowletter.api.exception.EntityNotFoundException
import org.springframework.stereotype.Service

interface IUserService {
    fun findByEmail(email: String): UserEntity
}

@Service
class UserService(
    private val userRepository: UserRepository,
) : IUserService {
    override fun findByEmail(email: String): UserEntity =
        userRepository.findByEmail(email) ?: throw EntityNotFoundException()
}