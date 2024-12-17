package com.example.springApi.Dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RefreshTokenDto {
    private String token;
    private String refreshToken;
}
