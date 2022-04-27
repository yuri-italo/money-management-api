package br.com.alura.moneymanagementapi.dto;

import br.com.alura.moneymanagementapi.model.Income;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IncomeDto {
    private final Long id;
    private final String description;
    private final BigDecimal value;
    private final LocalDate date;

    public IncomeDto(Income income) {
        this.id = income.getId();
        this.description = income.getDescription();
        this.value = income.getValue();
        this.date = income.getDate();
    }

    public static IncomeDto convertToDto(Income income) {
        return new IncomeDto(income);
    }

    public Long getId() {
        return id;
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
