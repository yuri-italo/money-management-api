package br.com.alura.moneymanagementapi.dto;

import br.com.alura.moneymanagementapi.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseDto {

    private final String description;
    private final BigDecimal value;
    private final LocalDate date;

    public ExpenseDto(Expense expense) {
        this.description = expense.getDescription();
        this.value = expense.getValue();
        this.date = expense.getDate();
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
}
