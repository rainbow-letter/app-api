package kr.co.rainbowletter.api.help

import org.springframework.stereotype.Service
import java.lang.management.ManagementFactory
import java.util.*

@Service
class HelpService(
    private val applicationInfo: ApplicationInfo,
) {

    fun health(): HealthResponse {

        val uptime = ManagementFactory.getRuntimeMXBean().uptime

        return HealthResponse(
            uptime = Date(uptime),
            info = applicationInfo)
    }
}