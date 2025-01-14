package kr.co.rainbowletter.api.help

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalTime

data class HealthResponse(
    @Schema(description = "Uptime of the service in HH:mm:ss format", example = "12:34:56", type = "string")
    val uptime: LocalTime,
    val info: ApplicationInfo,
)
