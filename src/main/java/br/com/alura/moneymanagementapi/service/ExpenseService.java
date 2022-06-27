package br.com.alura.moneymanagementapi.service;

import br.com.alura.moneymanagementapi.dto.MonthlyExpenseByCategoryDto;
import br.com.alura.moneymanagementapi.form.ExpenseForm;
import br.com.alura.moneymanagementapi.model.Category;
import br.com.alura.moneymanagementapi.model.Expense;
import br.com.alura.moneymanagementapi.repository.ExpenseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense save(ExpenseForm expenseForm) {
        return expenseRepository.save(new Expense(expenseForm));
    }

    public boolean expenseAlreadyExists(ExpenseForm expenseForm) {
        return expenseRepository.findByDescriptionAndDate(expenseForm.getDescription(),expenseForm.getDate().getYear(),expenseForm.getDate().getMonthValue());
    }

    public List<Expense> listAll(String description) {
        List<Expense> expenseList;

        if (description == null)
            expenseList = expenseRepository.findAll();
        else
            expenseList = expenseRepository.findByDescriptionContainingIgnoreCase(description);

        return expenseList;
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    public List<Expense> findByMonth(int year, int month) {
        return expenseRepository.findByMonth(year, month);
    }

    public Expense update(Long id, ExpenseForm expenseForm) {
        return updateFields(expenseRepository.getById(id),expenseForm);
    }

    public boolean isNotModified(Expense expense, ExpenseForm expenseForm) {
        return expense.getValue().compareTo(expenseForm.getValue()) == 0 && expenseForm.getCategory() == null;
    }

    private Expense updateFields(Expense expense, ExpenseForm expenseForm) {
        expense.setDescription(expenseForm.getDescription());
        expense.setValue(expenseForm.getValue());
        expense.setDate(expenseForm.getDate());

        if (expenseForm.getCategory() != null)
            expense.setCategory(expenseForm.getCategory());

        return expense;
    }

    public void delete(Expense expense) {
        expenseRepository.delete(expense);
    }

    public MonthlyExpenseByCategoryDto getExpenseByCategory(Category category) {
        BigDecimal total = expenseRepository.getTotalExpenseByCategory(category);

        return new MonthlyExpenseByCategoryDto(category,total);
    }

    public List<Expense> getExpenseListByMonth(int year, int month) {
        return expenseRepository.findByMonth(year,month);
    }

    public Expense patch(Map<String, Object> expenseFields, Expense expense) {
        ObjectMapper objectMapper = new ObjectMapper();
        Expense expenseData = objectMapper.convertValue(expenseFields, Expense.class);

        expenseFields.forEach((attribute,value) -> {
            Field field = ReflectionUtils.findField(Expense.class, attribute);
            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, expenseData);

            ReflectionUtils.setField(field,expense,newValue);
        });

        return expense;
    }
}


