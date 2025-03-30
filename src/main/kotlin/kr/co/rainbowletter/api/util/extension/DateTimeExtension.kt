package kr.co.rainbowletter.api.util.extension

import java.time.LocalDateTime
import java.time.LocalTime

fun LocalDateTime.startOfDay(): LocalDateTime =
    LocalDateTime.of(this.toLocalDate(), LocalTime.MIDNIGHT)