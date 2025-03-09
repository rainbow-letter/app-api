package kr.co.rainbowletter.api.scheduler

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/reports")
class LetterReportController (
    private val letterReportService: LetterReportService
) {
    @GetMapping
    fun getCustomLetterReport(
        @RequestParam("startDate") startDate: LocalDateTime?,
        @RequestParam("endDate") endDate: LocalDateTime?
    ): LetterReport {
        return letterReportService.getDailyLetterReport(startDate, endDate)
    }
}
