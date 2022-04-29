package br.com.alura.moneymanagementapi.dto;

import java.math.BigDecimal;
import java.util.List;

public class MonthSummaryDto {

    private final BigDecimal totalIncome;
    private final BigDecimal totalExpense;
    private final BigDecimal balance;
    private final List<MonthlyExpenseByCategoryDto> expenseByCategory;

    public MonthSummaryDto(BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal balance, List<MonthlyExpenseByCategoryDto> expenseByCategory) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
        this.expenseByCategory = expenseByCategory;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<MonthlyExpenseByCategoryDto> getExpenseByCategory() {
        return expenseByCategory;
    }
}
