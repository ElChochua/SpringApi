package com.example.springApi.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class AuthRequestDto {
    private String email;
    private String password;


}
