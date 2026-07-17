package com.diya.expensetrackerapi.dto;

public class LoginResponse {
    private String message = "Login Successful";

    public String getMessage() {
        return message;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
