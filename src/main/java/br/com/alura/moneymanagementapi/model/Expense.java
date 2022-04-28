package br.com.alura.moneymanagementapi.model;

import br.com.alura.moneymanagementapi.form.ExpenseForm;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private BigDecimal value;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    public Expense() {
    }

    public Expense(ExpenseForm expenseForm) {
        this.description = expenseForm.getDescription();
        this.value = expenseForm.getValue();
        this.date = expenseForm.getDate();

        if (expenseForm.getCategory() == null)
            this.category = Category.OTHERS;
        else
            this.category = expenseForm.getCategory();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
