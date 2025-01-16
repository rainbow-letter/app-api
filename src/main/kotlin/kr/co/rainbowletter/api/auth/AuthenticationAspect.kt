package kr.co.rainbowletter.api.auth

import kr.co.rainbowletter.api.exception.AuthenticationException
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Aspect
@Component
class AuthenticationAspect {
    @Before("@annotation(RequireAuthentication)")
    fun checkAuthentication() {
        SecurityContextHolder.getContext().authentication.principal
            ?: throw AuthenticationException()
    }
}
