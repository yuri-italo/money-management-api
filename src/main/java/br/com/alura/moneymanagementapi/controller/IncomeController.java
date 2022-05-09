package br.com.alura.moneymanagementapi.controller;

import br.com.alura.moneymanagementapi.form.IncomeForm;
import br.com.alura.moneymanagementapi.service.IncomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/incomes")
@Tag(name = "Incomes")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Save a new Income.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> save(@RequestBody @Valid IncomeForm incomeForm,UriComponentsBuilder uriBuilder) {
        return incomeService.save(incomeForm,uriBuilder);
    }
    @GetMapping
    @Operation(summary = "Return a list of incomes.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> listAll(@RequestParam(required = false) String description) {
        return incomeService.listAll(description);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get an income by id.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return incomeService.getById(id);
    }
    @GetMapping("/{year}/{month}")
    @Operation(summary = "Get an income by month.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getByMonth(@PathVariable int year, @PathVariable int month) {
        return incomeService.getByMonth(year,month);
    }
    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update one or more income fields.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody @Valid IncomeForm incomeForm) {
        return incomeService.updateById(id,incomeForm);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an income by id.")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        return incomeService.deleteById(id);
    }
}
