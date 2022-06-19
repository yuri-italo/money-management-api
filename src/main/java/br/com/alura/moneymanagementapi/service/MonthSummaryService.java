package br.com.alura.moneymanagementapi.service;

import br.com.alura.moneymanagementapi.dto.MonthSummaryDto;
import br.com.alura.moneymanagementapi.dto.MonthlyExpenseByCategoryDto;
import br.com.alura.moneymanagementapi.model.Category;
import br.com.alura.moneymanagementapi.model.Expense;
import br.com.alura.moneymanagementapi.model.Income;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MonthSummaryService {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    public MonthSummaryService(IncomeService incomeService, ExpenseService expenseService) {
        this.incomeService = incomeService;
        this.expenseService = expenseService;
    }

    public ResponseEntity<?>  get(int year, int month) {
        List<Income> incomeList = incomeService.getIncomeListByMonth(year, month);
        List<Expense> expenseList = expenseService.getExpenseListByMonth(year, month);

        MonthSummaryDto summary = getSummary(incomeList, expenseList);

        return ResponseEntity.status(HttpStatus.OK).body(summary);
    }

    private MonthSummaryDto getSummary(List<Income> incomeList, List<Expense> expenseList) {
        BigDecimal totalIncomes = incomeList.stream().map(Income::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalExpenses = expenseList.stream().map(Expense::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal balance = totalIncomes.subtract(totalExpenses);

        Set<Category> categories = expenseList.stream().map(Expense::getCategory).collect(Collectors.toSet());
        List<MonthlyExpenseByCategoryDto> expensesByCategory = new ArrayList<>();
        categories.forEach(category -> expensesByCategory.add(expenseService.getExpenseByCategory(category)));

        return new MonthSummaryDto(totalIncomes,totalExpenses,balance,expensesByCategory);
    }
}
