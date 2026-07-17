package com.diya.expensetrackerapi.controller;

import com.diya.expensetrackerapi.dto.LoginRequest;
import com.diya.expensetrackerapi.dto.LoginResponse;
import com.diya.expensetrackerapi.dto.UserResponse;
import com.diya.expensetrackerapi.dto.UserSignupRequest;
import com.diya.expensetrackerapi.model.User;
import com.diya.expensetrackerapi.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/api/auth/signup")
    public UserResponse signup (@RequestBody UserSignupRequest request) {
        User user = userService.registerUser(request.getUsername(), request.getPassword());
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setId(user.getId());
        return userResponse;
    }
    @PostMapping("/api/auth/login")
    public UserResponse login (@RequestBody LoginRequest request) {
        User user = userService.loginUser(request.getUsername(), request.getPassword());
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setId(user.getId());
        return userResponse;
    }
}
