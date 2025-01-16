package kr.co.rainbowletter.api.auth

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.rainbowletter.api.extensions.ServletRequestExtension.Companion.getBearerToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class JwtAuthenticationFilter(
    @Value("\${jwt.secret}") private val jwtSecret: String,
) : OncePerRequestFilter() {

    private var parser: JwtParser = Jwts.parser()
        .verifyWith(
            Keys.hmacShaKeyFor(
                Base64.getDecoder().decode(jwtSecret)
            )
        ).build()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            SecurityContextHolder.getContext().authentication = getAuthentication(request)
        } catch (_: Exception) {
            TODO("로깅 추가하기")
        } finally {
            filterChain.doFilter(request, response)
        }
    }

    fun getAuthentication(token: String): User? = try {
        val claims = parser.parseSignedClaims(token).payload
        User(
            claims.subject as String,
            claims["roles"] as String,
        )
    } catch (_: Exception) {
        // TODO("로깅 추가하기")
        null
    }

    fun getAuthentication(request: HttpServletRequest): Authentication {
        val token = request.getBearerToken()
        val user = token?.let { getAuthentication(it) }
        return UsernamePasswordAuthenticationToken(user, "", user?.getAuthorities())
    }

}