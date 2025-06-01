package com.example.springApi.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Data
@Entity
@Table(name = "requested_loans")
public class RequestedLoanModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requested_loan_id;
    private int organization_id;
    private int user_id;
    private int cicle_id;
    private String purpose;
    private float amount;
    private float interest_rate;
    private Date created_at;
    private Date limit_date;
    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }
}
