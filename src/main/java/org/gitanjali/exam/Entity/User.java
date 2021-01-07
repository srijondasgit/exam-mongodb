package org.gitanjali.exam.Entity;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "USER_TBL")
public class User {
    @Id
    private String id;
    private String userName;
    private String password;
    private String email;

    protected User() {

    }

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}