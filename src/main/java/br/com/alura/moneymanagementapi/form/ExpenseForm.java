package br.com.alura.moneymanagementapi.form;

import br.com.alura.moneymanagementapi.model.Category;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseForm {
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal value;
    @NotNull
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate date;
    private Category category;

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
