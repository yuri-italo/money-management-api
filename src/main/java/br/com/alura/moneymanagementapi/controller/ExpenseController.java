package br.com.alura.moneymanagementapi.controller;

import br.com.alura.moneymanagementapi.dto.ExpenseDto;
import br.com.alura.moneymanagementapi.dto.ExpenseWithoutIdDto;
import br.com.alura.moneymanagementapi.form.ExpenseForm;
import br.com.alura.moneymanagementapi.model.Expense;
import br.com.alura.moneymanagementapi.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        if (expenseService.expenseAlreadyExists(expenseForm))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Monthly expense already exists.");

        Expense savedExpense = expenseService.save(expenseForm);

        return ResponseEntity.created(uriBuilder.path("/expenses/{id}").buildAndExpand(savedExpense.getId()).toUri()).body(new ExpenseDto(savedExpense));
    }

    @GetMapping
    @Operation(summary = "Return a list of expenses.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> listAll(@RequestParam(required = false) String description) {
        List<Expense> list = expenseService.listAll(description);

        if (list.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.OK).body(ExpenseDto.convertManyToDto(list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an expense by id.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Expense> optional = expenseService.findById(id);

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new ExpenseWithoutIdDto(optional.get()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense does not exist.");
    }

    @GetMapping("/{year}/{month}")
    @Operation(summary = "Get an expense by month")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getByMonth(@PathVariable int year, @PathVariable int month) {
        List<Expense> list = expenseService.findByMonth(year, month);

        if (list.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.OK).body(ExpenseWithoutIdDto.convertManyToDto(list));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update an expense.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody @Valid ExpenseForm expenseForm) {
        Optional<Expense> optional = expenseService.findById(id);

        if (optional.isPresent()) {
            if (expenseService.expenseAlreadyExists(expenseForm) && expenseService.isNotModified(optional.get(),expenseForm))
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Monthly expense already exists.");

            Expense updatedExpense = expenseService.update(id, expenseForm);

            return ResponseEntity.status(HttpStatus.OK).body(new ExpenseWithoutIdDto(updatedExpense));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense does not exist.");
    }

    @PatchMapping("/{id}")
    @Transactional
    @Operation(summary = "Update one or more fields.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> patch(@PathVariable Long id, @RequestBody Map<String, Object> expenseFields) {
        Optional<Expense> optional = expenseService.findById(id);

        if (optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense does not exist.");

        Expense updatedExpense = expenseService.patch(expenseFields,optional.get());

        return ResponseEntity.status(HttpStatus.OK).body(new ExpenseWithoutIdDto(updatedExpense));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete an expense by id.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Expense> optional = expenseService.findById(id);

        if (optional.isPresent()) {
            expenseService.delete(optional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Expense deleted.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense does not exist.");
    }
}
