package br.com.alura.moneymanagementapi.dto;

import br.com.alura.moneymanagementapi.model.Category;

import java.math.BigDecimal;

public class MonthlyExpenseByCategoryDto {
    private final String category;
    private final BigDecimal total;

    public MonthlyExpenseByCategoryDto(Category category, BigDecimal total) {
        this.category = String.valueOf(category);
        this.total = total;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
