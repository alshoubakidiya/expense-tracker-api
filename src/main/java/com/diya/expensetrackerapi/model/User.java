package com.diya.expensetrackerapi.model;

import jakarta.persistence.*;

@Entity //This annotation tells the database to create a table for users
@Table(name = "app_user")
public class User {
    @Id //This is the unique key for each User
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto creates an ID by incrementing a value
    private Long id;

    private String username;
    private String password;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
