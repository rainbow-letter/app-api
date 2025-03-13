package kr.co.rainbowletter.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addFormatters(registry: org.springframework.format.FormatterRegistry) {
        val registrar = DateTimeFormatterRegistrar()
        registrar.setDateTimeFormatter(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
        registrar.registerFormatters(registry)
    }
}
