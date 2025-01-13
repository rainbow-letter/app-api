package kr.co.rainbowletter.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class RainbowletterApiApplication

fun main(args: Array<String>) {
    runApplication<RainbowletterApiApplication>(*args)
}
