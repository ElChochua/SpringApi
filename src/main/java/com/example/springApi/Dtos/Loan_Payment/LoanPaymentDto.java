package com.example.springApi.Dtos.Loan_Payment;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class LoanPaymentDto {
    private int payment_ID;
    private int loan_ID;
    private int created_by;
    private String payment_date;
    private double payment_amount;
    private String payment_method;
    private String status;
    private String created_at;
}
