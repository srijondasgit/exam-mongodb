package org.gitanjali.exam.Entity;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "USER_TBL")
public class User {
    @Id
    private String id;
    private String userName;
    private String password;
    private String email;
    private String role;

    protected User() {

    }

    public User(String userName, String password, String email, String role) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;

    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}