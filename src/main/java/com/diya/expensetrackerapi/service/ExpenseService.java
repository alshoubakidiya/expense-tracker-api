package com.diya.expensetrackerapi.service;

import com.diya.expensetrackerapi.dto.ExpenseRequest;
import com.diya.expensetrackerapi.dto.ExpenseResponse;
import com.diya.expensetrackerapi.exception.ExpenseNotFoundException;
import com.diya.expensetrackerapi.exception.InvalidCredentialsException;
import com.diya.expensetrackerapi.model.Expense;
import com.diya.expensetrackerapi.model.User;
import com.diya.expensetrackerapi.repository.ExpenseRepository;
import com.diya.expensetrackerapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }
    public Expense createExpense(String username, String description, BigDecimal amount, String category, LocalDate date) {

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            Expense expense = new Expense();
            expense.setDescription(description);
            expense.setCategory(category);
            expense.setAmount(amount);
            expense.setDate(date);
            expense.setUser(user.get());
            return expenseRepository.save(expense);
        }
        else{
            throw new InvalidCredentialsException("Failed to get user ");
        }
    }
    public List<Expense> getExpensesForUser(String username) {
       return  expenseRepository.findByUserUsername(username);
    }
    public Expense getExpenseById(Long id, String username) {
        Optional<Expense> expense = expenseRepository.findByIdAndUserUsername(id, username);
        if (expense.isPresent()) {
            return expense.get();
        } else{
            throw new ExpenseNotFoundException("No matching expense found for given id: "+id);
        }
    }
    public Expense updateExpenseById(Long id, String username, ExpenseRequest request) {
        Optional<Expense> expense = expenseRepository.findByIdAndUserUsername(id, username);
        if (expense.isPresent()) {
            if (request.getDescription() != null) {
                expense.get().setDescription(request.getDescription());
            }
            if (request.getCategory() != null) {
                expense.get().setCategory(request.getCategory());
            }
            if (request.getAmount() != null) {
                expense.get().setAmount(request.getAmount());
            }
            if (request.getDate() != null) {
                expense.get().setDate(request.getDate());
            }
        } else{
            throw new ExpenseNotFoundException("No matching expense found for given id: "+id);
        }
        return expenseRepository.save(expense.get());
    }
    public Expense deleteExpenseById(Long id, String username) {
        Optional<Expense> expense = expenseRepository.findByIdAndUserUsername(id, username);
        if (expense.isPresent()) {
            expenseRepository.delete(expense.get());
            return expense.get();

        }else{
            throw new ExpenseNotFoundException("No matching expense found for given id: "+id);
        }
    }
}
