package com.diya.expensetrackerapi.repository;

import com.diya.expensetrackerapi.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserUsername(String username);
    Optional<Expense> findByIdAndUserUsername(Long id, String username);
    List<Expense> findByUserUsernameAndDateBetween(String userUsername, LocalDate startDate, LocalDate endDate);
}
