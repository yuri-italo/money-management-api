package br.com.alura.moneymanagementapi.controller;

import br.com.alura.moneymanagementapi.form.ExpenseForm;
import br.com.alura.moneymanagementapi.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody @Valid ExpenseForm expenseForm, UriComponentsBuilder uriBuilder) {
        return expenseService.save(expenseForm,uriBuilder);
    }
}
