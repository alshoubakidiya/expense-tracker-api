package com.diya.expensetrackerapi.controller;

import com.diya.expensetrackerapi.dto.ExpenseRequest;
import com.diya.expensetrackerapi.dto.ExpenseResponse;
import com.diya.expensetrackerapi.model.Expense;
import com.diya.expensetrackerapi.service.ExpenseService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ExpenseController {

    private final ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    @PostMapping("/api/expenses")
    public ExpenseResponse addExpense(@RequestBody ExpenseRequest expenseRequest) {
       Expense expense = expenseService.createExpense(SecurityContextHolder.getContext().getAuthentication().getName(), expenseRequest.getDescription(),
                expenseRequest.getAmount(), expenseRequest.getCategory(), expenseRequest.getDate());
        ExpenseResponse expenseResponse = new ExpenseResponse();
        expenseResponse.setId(expense.getId());
        expenseResponse.setDescription(expense.getDescription());
        expenseResponse.setAmount(expense.getAmount());
        expenseResponse.setCategory(expense.getCategory());
        expenseResponse.setDate(expense.getDate());
        return expenseResponse;
    }
    @GetMapping("/api/expenses")
    public List<ExpenseResponse> getMyExpenses() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Expense> expenses = expenseService.getExpensesForUser(username);
        List<ExpenseResponse> expenseResponseList = new ArrayList<>();
        for (Expense expense : expenses) {
            ExpenseResponse expenseResponse = new ExpenseResponse();
            expenseResponse.setId(expense.getId());
            expenseResponse.setDescription(expense.getDescription());
            expenseResponse.setAmount(expense.getAmount());
            expenseResponse.setCategory(expense.getCategory());
            expenseResponse.setDate(expense.getDate());
            expenseResponseList.add(expenseResponse);
        }
        //Convert each Expense into an ExpenseResponse, collect into a list, return it
        return expenseResponseList;
    }
    @GetMapping("/api/expenses/{id}")
    public ExpenseResponse getExpenseById(@PathVariable long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Expense expense = expenseService.getExpenseById(id, username);
        ExpenseResponse expenseResponse = new ExpenseResponse();
        expenseResponse.setId(expense.getId());
        expenseResponse.setDescription(expense.getDescription());
        expenseResponse.setAmount(expense.getAmount());
        expenseResponse.setCategory(expense.getCategory());
        expenseResponse.setDate(expense.getDate());
        return expenseResponse;
    }
}
