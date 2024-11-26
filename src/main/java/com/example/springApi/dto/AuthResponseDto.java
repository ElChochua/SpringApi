package com.example.springApi.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    String token;
    String tokenRefresh;
}
