package com.example.springApi.Dtos.UserCreditsDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateUserRoleDto {
    private int user_id;
    private String role;
}
