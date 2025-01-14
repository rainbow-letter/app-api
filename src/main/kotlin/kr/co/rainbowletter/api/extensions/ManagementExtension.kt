package kr.co.rainbowletter.api.extensions

import java.lang.management.RuntimeMXBean
import java.time.LocalTime

class ManagementExtension {
    companion object {
        fun RuntimeMXBean.uptimeToTime(): LocalTime {
            val uptime = this.uptime

            return LocalTime.of(
                (uptime / (1000 * 60 * 60)).toInt(),
                ((uptime / (1000 * 60)) % 60).toInt(),
                ((uptime / 1000) % 60).toInt()
            );
        }
    }
}