package com.example.springApi.dto.authDto;

import com.example.springApi.Model.UserModel;
import com.example.springApi.dto.authDto.UsersDtos.UsersDto;
import lombok.Data;

@Data
public class    AuthResponseDto {
    String token;
    String refreshToken;
    UsersDto user;
}
