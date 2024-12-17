package com.example.springApi.Dtos.LoanDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RequestLoanDto {
    private int organization_id;
    private int loan_applicant_id;
    private double amount;
    private double interest_rate;
    private int term_value;
    private String term_unit;
    private String purpose;
    private String currency;
}
