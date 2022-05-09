package br.com.alura.moneymanagementapi.controller;

import br.com.alura.moneymanagementapi.service.MonthSummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/summaries")
@Tag(name = "Summary")
public class MonthSummaryController {
    private final MonthSummaryService monthSummaryService;

    public MonthSummaryController(MonthSummaryService monthSummaryService) {
        this.monthSummaryService = monthSummaryService;
    }

    @GetMapping("/{year}/{month}")
    @Operation(summary = "Get the month summary.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> get(@PathVariable int year, @PathVariable int month) {
        return monthSummaryService.get(year,month);
    }
}
