package com.example.springApi.Dtos.LoanDtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class LoanDto {
    private int loan_id;
    private int organization_id;
    private int loan_applicant_id;
    private double amount;
    private double interest_rate;
    private int term_value;
    private String term_unit;
    private String status;
    private String issued_at;
    private String due_at;
    private String approved_at;
    private String purpose;
    private String currency;
    private double total_amount_due;
}
