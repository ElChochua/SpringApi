package com.example.springApi.dto;

import lombok.Data;
import lombok.Setter;

@Data
public class RegisterDto {

    private String user;
    private String email;
    private String password;

}
