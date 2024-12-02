package com.example.springApi.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoanRepository implements ILoanInterface {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void registerLoan() {
        System.out.println("Loan registered");
    }
}
