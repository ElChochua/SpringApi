package com.example.springApi.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "loans")
public class LoanModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loan_id;
    private int organization_id;
    private int user_id;
    private int cicle_id;
    private String purpose;
    private float amount;
    private float interest_rate;
    private Date start_date;
    private Date end_date;
    private Date created_at;
    @Enumerated(EnumType.STRING)
    private LoanStatus status; // Status can be "active", "closed", "defaulted", etc.
    public enum LoanStatus {
        ACTIVE,
        CLOSED,
        DEFAULTED
    }
}
