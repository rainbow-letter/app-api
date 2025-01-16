package kr.co.rainbowletter.api.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import kr.co.rainbowletter.api.help.ApplicationInfo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders


@Configuration
class SwaggerConfig(
    private val applicationInfo: ApplicationInfo
) {
    private val securityName: String = "Authorization"

    private val info: Info
        get() = Info()
            .version(applicationInfo.version)
            .description(applicationInfo.name)

    private val security: SecurityScheme
        get() = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat(securityName)
            .`in`(SecurityScheme.In.HEADER)
            .name(HttpHeaders.AUTHORIZATION)

    private val securityRequirement: SecurityRequirement
        get() = SecurityRequirement()
            .addList(securityName)

    @Bean
    fun openApi(): OpenAPI = OpenAPI()
        .components(Components().addSecuritySchemes(securityName, security))
        .addSecurityItem(securityRequirement)
        .info(info)
}
