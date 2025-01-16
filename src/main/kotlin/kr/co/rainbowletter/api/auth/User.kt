package kr.co.rainbowletter.api.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class User(
    public val email: String,
    public val roles: String,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<GrantedAuthority> = ArrayList()
    override fun getPassword(): String = ""
    override fun getUsername(): String = ""
}