package com.example.springApi.dto.authDto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RegisterDto {
    private String email;
    private String password;
    private String username;
}
