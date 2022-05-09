package br.com.alura.moneymanagementapi.controller;

import br.com.alura.moneymanagementapi.form.ExpenseForm;
import br.com.alura.moneymanagementapi.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/expenses")
@Tag(name = "Expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Create a new expense.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> save(@RequestBody @Valid ExpenseForm expenseForm, UriComponentsBuilder uriBuilder) {
        return expenseService.save(expenseForm,uriBuilder);
    }
    @GetMapping
    @Operation(summary = "Return a list of expenses.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> listAll(@RequestParam(required = false) String description) {
      return expenseService.listAll(description);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get an expense by id.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return expenseService.getById(id);
    }
    @GetMapping("/{year}/{month}")
    @Operation(summary = "Get an expense by month")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getByMonth(@PathVariable int year, @PathVariable int month) {
        return expenseService.getByMonth(year,month);
    }
    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update one or more expense fields by id.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody @Valid ExpenseForm expenseForm) {
        return expenseService.updateById(id,expenseForm);
    }
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete an expense by id.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return expenseService.deleteById(id);
    }
}
