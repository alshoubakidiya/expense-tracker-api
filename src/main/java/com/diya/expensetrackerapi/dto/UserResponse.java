package com.diya.expensetrackerapi.dto;

public class UserResponse {
    private long id;
    private String username;

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
}
