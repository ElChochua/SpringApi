package com.example.springApi.Dtos.UserCreditsDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserCreditsDto {
    private int credit_ID;
    private int user_ID;
    private int organization_ID;
    private double credit_score;
    private double credit_limit;
    private double available_credit;
    private String updated_at;
}
