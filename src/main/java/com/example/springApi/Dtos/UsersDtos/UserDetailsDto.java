package com.example.springApi.Dtos.UsersDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserDetailsDto {
    private int user_id;
    private String user_name;
    private String email;
    private String name;
    private String last_name;
    private String birthdate;
    private String curp;
    private String status;
    private String created_at;
    private String role;
    private String phone_number;
}
