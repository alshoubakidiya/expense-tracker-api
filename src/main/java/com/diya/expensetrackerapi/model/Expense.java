package com.diya.expensetrackerapi.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity //This annotation tells the database to create a table for expenses
public class Expense {
    @Id //This is the unique key for each expense
    @GeneratedValue(strategy = GenerationType.IDENTITY) //It generates ID automatically by incrementing the count of expenses
    private Long id;
    private String description;
    private BigDecimal amount;
    private String category;
    private LocalDate date;

    @ManyToOne //Makes it so many expenses can be logged under one account
    @JoinColumn(name = "user_id")
    private User user;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    public void setAmount(BigDecimal amount) {
      this.amount = amount;
    }
    public BigDecimal getAmount() {
      return amount;
    }
    public void setCategory(String category) {
      this.category = category;
    }
    public String getCategory() {
      return category;
    }
    public void setDate(LocalDate date) {
      this.date = date;
    }
    public LocalDate getDate() {
      return date;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
