package kr.co.rainbowletter.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
@EnableFeignClients
class RainbowletterApiApplication

fun main(args: Array<String>) {
    runApplication<RainbowletterApiApplication>(*args)
}
