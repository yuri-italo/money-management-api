package br.com.alura.moneymanagementapi.controller;

import br.com.alura.moneymanagementapi.form.IncomeForm;
import br.com.alura.moneymanagementapi.service.IncomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody @Valid IncomeForm incomeForm,UriComponentsBuilder uriBuilder) {
        return incomeService.save(incomeForm,uriBuilder);
    }
}
