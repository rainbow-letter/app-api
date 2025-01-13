package kr.co.rainbowletter.api.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import kr.co.rainbowletter.api.help.ApplicationInfo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig(
    private val applicationInfo: ApplicationInfo
) {

    private val info: Info
        get() = Info()
            .version(applicationInfo.version)
            .description(applicationInfo.name)

    @Bean
    fun openApi(): OpenAPI = OpenAPI().info(info)
}
