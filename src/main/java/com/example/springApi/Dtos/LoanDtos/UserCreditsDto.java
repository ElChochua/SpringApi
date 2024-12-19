package com.example.springApi.Dtos.LoanDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserCreditsDto {
    private int User_ID;
    private int credit_ID;
    private int organization_ID;
    private double credit_limit;
    private double credit_avialable;
    private double credit_score;
    private String updated_at;
}
