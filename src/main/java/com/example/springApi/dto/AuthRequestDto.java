package com.example.springApi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class AuthRequestDto {
    private String email;
    private String password;

}
