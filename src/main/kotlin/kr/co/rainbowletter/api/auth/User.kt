package kr.co.rainbowletter.api.auth

import kr.co.rainbowletter.api.data.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class User(
    val email: String,
    val role: Role,
    val userEntity: UserEntity,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<GrantedAuthority> = ArrayList()
    override fun getPassword(): String = ""
    override fun getUsername(): String = ""
}

enum class Role {
    ROLE_USER,
    ROLE_ADMIN,
}