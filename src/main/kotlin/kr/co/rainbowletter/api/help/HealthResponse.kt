package kr.co.rainbowletter.api.help

import java.util.Date

data class HealthResponse(
    val uptime: Date,
    val info: ApplicationInfo,
)
