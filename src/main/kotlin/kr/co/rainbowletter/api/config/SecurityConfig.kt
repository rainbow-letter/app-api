package kr.co.rainbowletter.api.config

import kr.co.rainbowletter.api.auth.CorsFilter
import kr.co.rainbowletter.api.auth.JwtAuthenticationFilter
import kr.co.rainbowletter.api.auth.RequestLogFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.handler.HandlerMappingIntrospector

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val corsFilter: CorsFilter,
    private val requestLogFilter: RequestLogFilter,
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(
        http: HttpSecurity,
        introspector: HandlerMappingIntrospector?,
    ): SecurityFilterChain {
        return http
            .csrf { o -> o.disable() }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(requestLogFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}
