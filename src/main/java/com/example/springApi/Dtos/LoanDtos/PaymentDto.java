package com.example.springApi.Dtos.LoanDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PaymentDto {
    private int loan_ID;
    private int user_ID;
    private String transaction_type;
    private double amount;
    private String issued_at;
    private String description;
}
