package com.example.springApi.Dtos.UsersDtos;

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
