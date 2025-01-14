package kr.co.rainbowletter.api.help

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/help")
@Tag(name = "help")
class HelpController(
    private val helpService: HelpService
) {
    @GetMapping("/health")
    fun help(): HealthResponse = helpService.health()
}