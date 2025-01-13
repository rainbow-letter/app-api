package kr.co.rainbowletter.api.help

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.application")
data class ApplicationInfo(
    var name: String,
    var version: String,
)