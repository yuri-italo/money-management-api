package br.com.alura.moneymanagementapi.controller;

import br.com.alura.moneymanagementapi.dto.IncomeDto;
import br.com.alura.moneymanagementapi.dto.IncomeWithoutIdDto;
import br.com.alura.moneymanagementapi.form.IncomeForm;
import br.com.alura.moneymanagementapi.model.Income;
import br.com.alura.moneymanagementapi.service.IncomeService;
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
        if (incomeService.incomeAlreadyExists(incomeForm))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Monthly income already exists.");

        Income savedIncome = incomeService.save(incomeForm);

        return ResponseEntity.created(uriBuilder.path("/incomes/{id}").buildAndExpand(savedIncome.getId()).toUri()).body(new IncomeDto(savedIncome));
    }

    @GetMapping
    @Operation(summary = "Return a list of incomes.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> listAll(@RequestParam(required = false) String description) {
        List<Income> incomeList = incomeService.listAll(description);

        if (incomeList.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.OK).body(IncomeDto.convertManyToDto(incomeList));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an income by id.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Income> optional = incomeService.findById(id);

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new IncomeWithoutIdDto(optional.get()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income does not exist.");
    }

    @GetMapping("/{year}/{month}")
    @Operation(summary = "Get an income by month.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getByMonth(@PathVariable int year, @PathVariable int month) {
        List<Income> incomeList = incomeService.getByMonth(year, month);

        if (incomeList.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.OK).body(IncomeWithoutIdDto.convertManyToDto(incomeList));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update an income.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody @Valid IncomeForm incomeForm) {
        Optional<Income> optional = incomeService.findById(id);

        if (optional.isPresent()) {
            if (incomeService.incomeAlreadyExists(incomeForm) && incomeService.valueIsEqual(optional.get(),incomeForm))
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Monthly income already exists.");

            Income updatedIncome = incomeService.update(id, incomeForm);

            return ResponseEntity.status(HttpStatus.OK).body(new IncomeWithoutIdDto(updatedIncome));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income does not exist.");
    }

    @PatchMapping("/{id}")
    @Transactional
    @Operation(summary = "Update one or more fields.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> patch(@PathVariable Long id, @RequestBody Map<String,Object> incomeFields) {
        Optional<Income> optional = incomeService.findById(id);

        if (optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income does not exist.");

        Income updatedIncome = incomeService.patch(incomeFields,optional.get());

        return ResponseEntity.status(HttpStatus.OK).body(new IncomeWithoutIdDto(updatedIncome));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an income by id.")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        Optional<Income> optional = incomeService.findById(id);

        if (optional.isPresent()) {
            incomeService.delete(optional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Income deleted.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income does not exist.");
    }
}
