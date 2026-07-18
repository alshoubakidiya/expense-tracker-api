package com.diya.expensetrackerapi.dto;

public class LoginResponse {
    private String message = "Login Successful";
    private String token;

    public String getMessage() {
        return message;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
