package com.diya.expensetrackerapi.dto;

import com.diya.expensetrackerapi.model.Category;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseRequest {
    private String description;
    private BigDecimal amount;
    @Schema(description = "One of: GROCERIES, LEISURE, ELECTRONICS, UTILITIES, CLOTHING, HEALTH, OTHERS")
    private Category category;
    private LocalDate date;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
