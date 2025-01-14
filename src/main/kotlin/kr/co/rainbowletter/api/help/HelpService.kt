package kr.co.rainbowletter.api.help

import kr.co.rainbowletter.api.extensions.ManagementExtension.Companion.uptimeToTime
import org.springframework.stereotype.Service
import java.lang.management.ManagementFactory

@Service
class HelpService(
    private val applicationInfo: ApplicationInfo,
) {

    fun health(): HealthResponse {

        val uptime = ManagementFactory.getRuntimeMXBean().uptime

        return HealthResponse(
            uptime = ManagementFactory.getRuntimeMXBean().uptimeToTime(),
            info = applicationInfo
        )
    }
}