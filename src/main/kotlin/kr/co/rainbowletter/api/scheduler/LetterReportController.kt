package kr.co.rainbowletter.api.scheduler

import io.swagger.v3.oas.annotations.Operation
import kr.co.rainbowletter.api.auth.RequireAuthenticationWithAdmin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api")
class LetterReportController(
    private val letterReportService: LetterReportService
) {
    @Operation(summary = "로그인한 사용자가 작성한 편지 조회")
    @GetMapping("/admin/letters/report")
    @RequireAuthenticationWithAdmin
    fun getCustomLetterReport(
        @RequestParam("startDate") startDate: LocalDateTime?,
        @RequestParam("endDate") endDate: LocalDateTime?
    ): LetterReportResponse {
        return letterReportService.report(startDate, endDate)
    }
}
