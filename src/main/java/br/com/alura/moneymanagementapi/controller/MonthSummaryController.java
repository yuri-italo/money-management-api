package br.com.alura.moneymanagementapi.controller;

import br.com.alura.moneymanagementapi.service.MonthSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/summaries")
public class MonthSummaryController {
    private final MonthSummaryService monthSummaryService;

    public MonthSummaryController(MonthSummaryService monthSummaryService) {
        this.monthSummaryService = monthSummaryService;
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> get(@PathVariable int year, @PathVariable int month) {
        return monthSummaryService.get(year,month);
    }
}
