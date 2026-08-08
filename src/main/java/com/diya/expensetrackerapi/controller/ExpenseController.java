package com.diya.expensetrackerapi.controller;

import com.diya.expensetrackerapi.dto.ExpenseRequest;
import com.diya.expensetrackerapi.dto.ExpenseResponse;
import com.diya.expensetrackerapi.model.Expense;
import com.diya.expensetrackerapi.service.ExpenseService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
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
    @PutMapping("/api/expenses/{id}")
    public ExpenseResponse modifyExpenseById(@PathVariable long id, @RequestBody ExpenseRequest expenseRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Expense expense = expenseService.updateExpenseById(id, username, expenseRequest);
        ExpenseResponse expenseResponse = new ExpenseResponse();
        expenseResponse.setId(expense.getId());
        expenseResponse.setDescription(expense.getDescription());
        expenseResponse.setAmount(expense.getAmount());
        expenseResponse.setCategory(expense.getCategory());
        expenseResponse.setDate(expense.getDate());
        return expenseResponse;
    }
    @DeleteMapping("/api/expenses/{id}")
    public String deleteExpenseById(@PathVariable long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Expense expense = expenseService.deleteExpenseById(id, username);
        return "Expense with id: " + expense.getId() + " and description: \"" + expense.getDescription() + "\" was deleted";
    }
    @GetMapping("/api/expenses/filter")
    public List<ExpenseResponse> getExpensesByFilter(
            @RequestParam(required = false) String range,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        LocalDate today = LocalDate.now();

        if (range != null && range.equals("week")) {
            startDate = today.minusWeeks(1);
            endDate = today;
        } else if (range != null && range.equals("month")) {
            startDate = today.minusMonths(1);
            endDate = today;
        } else if (range != null && range.equals("3months")) {
            startDate = today.minusMonths(3);
            endDate = today;
        }
            List<Expense> expenses = expenseService.getExpensesByDateRange(username, startDate, endDate);
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

// if none of these matched, startDate/endDate stay whatever the client sent directly (custom case)

    }
}
