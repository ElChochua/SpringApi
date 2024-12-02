package com.example.springApi.dto.authDto.UsersDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UsersDto {
    private int user_id;
    private String user;
    private String email;
    private String role;
}
