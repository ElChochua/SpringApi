package com.example.springApi.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table (name = "aportations")
public class AportationsModel {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int aportation_id;
    private int organization_id;
    private int user_id;
    private int cicle_id;
    private float amount;
    private Date created_at;
}
