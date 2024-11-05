package org.example.springboot.entity;

import lombok.Data;

@Data
public class User {
    int id;
    String email;
    String password;
    String userName;
    String inputCode;
    public User(String username, String email, String password) {
        this.userName = username;
        this.email = email;
        this.password = password;
    }


}
