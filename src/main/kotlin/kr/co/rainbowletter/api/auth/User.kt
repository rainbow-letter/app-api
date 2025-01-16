package kr.co.rainbowletter.api.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class User(
    private val email: String,
    private val roles: String,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<GrantedAuthority> = ArrayList()
    override fun getPassword(): String = ""
    override fun getUsername(): String = ""
}