package br.com.alura.moneymanagementapi.service;

import br.com.alura.moneymanagementapi.dto.ExpenseDto;
import br.com.alura.moneymanagementapi.form.ExpenseForm;
import br.com.alura.moneymanagementapi.model.Expense;
import br.com.alura.moneymanagementapi.repository.ExpenseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public ResponseEntity<?> save(ExpenseForm expenseForm, UriComponentsBuilder uriBuilder) {
        if (expenseAlreadyExists(expenseForm))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Monthly expense already exists.");

        Expense expense = new Expense();
        BeanUtils.copyProperties(expenseForm,expense);
        expenseRepository.save(expense);

        return ResponseEntity.created(uriBuilder.path("/expenses/{id}").buildAndExpand(expense.getId()).toUri()).body(new ExpenseDto(expense));
    }

    private boolean expenseAlreadyExists(ExpenseForm expenseForm) {
        return expenseRepository.findByDescriptionAndDate(expenseForm.getDescription(),expenseForm.getDate().getYear(),expenseForm.getDate().getMonthValue());
    }

    public ResponseEntity<?> listAll() {
        List<Expense> expenseList = expenseRepository.findAll();

        if (expenseList.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.OK).body(ExpenseDto.convertManyToDto(expenseList));
    }

    public ResponseEntity<?> getById(Long id) {
        Optional<Expense> optional = expenseRepository.findById(id);

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new ExpenseDto(optional.get()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense does not exist.");
    }

    public ResponseEntity<?> updateById(Long id, ExpenseForm expenseForm) {
        Optional<Expense> optional = expenseRepository.findById(id);

        if (expenseAlreadyExists(expenseForm))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Monthly expense already exists.");

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(updateFields(optional.get(),expenseForm));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense does not exist.");
    }

    private ExpenseDto updateFields(Expense expense, ExpenseForm expenseForm) {
        expense.setDescription(expenseForm.getDescription());
        expense.setValue(expenseForm.getValue());
        expense.setDate(expenseForm.getDate());

        return new ExpenseDto(expense);
    }

    public ResponseEntity<?> deleteById(Long id) {
        Optional<Expense> optional = expenseRepository.findById(id);

        if (optional.isPresent()) {
            expenseRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Expense deleted.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense does not exist.");
    }
}
