package com.diya.expensetrackerapi.repository;

import com.diya.expensetrackerapi.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
