package com.example.springApi.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(generator = "auto")
    Integer User_ID;
    String User;
    String Email;
    String Password;
    String Token;
    String Role;

    public Users(int userId, String user, String email, String password, String role) {
        this.User_ID = userId;
        this.User = user;
        this.Email = email;
        this.Password = password;
        this.Role = role;
    }
}
