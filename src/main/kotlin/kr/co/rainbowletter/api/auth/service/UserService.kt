package kr.co.rainbowletter.api.auth.service

import kr.co.rainbowletter.api.data.entity.UserEntity
import kr.co.rainbowletter.api.data.repository.UserRepository
import kr.co.rainbowletter.api.exception.EntityNotFoundException
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import kotlin.reflect.jvm.jvmName

interface IUserService {
    fun findByEmail(email: String): UserEntity
}

@Service
class UserService(
    private val userRepository: UserRepository,
) : IUserService {

    @Cacheable(cacheNames = ["user-email"], key = "#email")
    override fun findByEmail(email: String): UserEntity =
        userRepository.findByEmail(email) ?: throw EntityNotFoundException(UserEntity::class.jvmName, email)
}