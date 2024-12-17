package com.example.springApi.Dtos;

import com.example.springApi.Dtos.UsersDtos.UsersDto;
import lombok.Data;

@Data
public class LoginDto {
    String token;
    String refreshToken;
    UsersDto user;
}
