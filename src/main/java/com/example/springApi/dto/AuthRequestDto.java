package com.example.springApi.dto;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String user;
    private String password;

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
