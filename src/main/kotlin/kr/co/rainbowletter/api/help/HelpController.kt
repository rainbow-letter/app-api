package kr.co.rainbowletter.api.help

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/help")
class HelpController(
    private val helpService: HelpService
) {

    @GetMapping("/health")
    fun help(): HealthResponse = helpService.health()
}