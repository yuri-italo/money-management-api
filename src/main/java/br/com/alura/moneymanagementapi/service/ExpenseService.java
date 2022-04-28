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
}
