package kr.co.rainbowletter.api.auth

import kr.co.rainbowletter.api.exception.AuthenticationException
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Aspect
@Component
class AuthenticationAspect {
    private fun getUser(): User? {
        val user = SecurityContextHolder.getContext()?.authentication?.principal

        return if (user == null) null
        else user as User
    }

    @Before("@annotation(RequireAuthentication)")
    fun checkAuthentication() {
        getUser() ?: throw AuthenticationException()
    }

    @Before("@annotation(RequireAuthenticationWithAdmin)")
    fun checkAuthenticationWithAdmin() {
        val user = getUser() ?: throw AuthenticationException()

        user.role == Role.ROLE_ADMIN || throw AuthenticationException()
    }
}
