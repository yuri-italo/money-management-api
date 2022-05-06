package br.com.alura.moneymanagementapi.dto;

import br.com.alura.moneymanagementapi.model.Category;
import br.com.alura.moneymanagementapi.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseWithoutIdDto {

    private final String description;
    private final BigDecimal value;
    private final LocalDate date;
    private final Category category;

    public ExpenseWithoutIdDto(Expense expense) {
        this.description = expense.getDescription();
        this.value = expense.getValue();
        this.date = expense.getDate();
        this.category = expense.getCategory();
    }

    public static List<ExpenseWithoutIdDto> convertManyToDto(List<Expense> expenseList) {
        return expenseList.stream().map(ExpenseWithoutIdDto::new).collect(Collectors.toList());
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public LocalDate getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }
}
