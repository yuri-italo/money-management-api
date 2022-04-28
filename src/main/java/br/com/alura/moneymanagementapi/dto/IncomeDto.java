package br.com.alura.moneymanagementapi.dto;

import br.com.alura.moneymanagementapi.model.Income;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class IncomeDto {
    private final String description;
    private final BigDecimal value;
    private final LocalDate date;

    public IncomeDto(Income income) {
        this.description = income.getDescription();
        this.value = income.getValue();
        this.date = income.getDate();
    }

    public static List<IncomeDto> convertManyToDto(List<Income> incomeList) {
        return incomeList.stream().map(IncomeDto::new).collect(Collectors.toList());
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
