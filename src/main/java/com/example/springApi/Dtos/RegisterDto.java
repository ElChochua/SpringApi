package com.example.springApi.Dtos;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RegisterDto {
    private String email;
    private String password;
    private String username;
}
