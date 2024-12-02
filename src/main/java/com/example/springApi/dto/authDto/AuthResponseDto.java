package com.example.springApi.dto.authDto;

import lombok.Data;

@Data
public class AuthResponseDto {
    String token;
    String tokenRefresh;
}
