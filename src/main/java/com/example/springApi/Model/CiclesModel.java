package com.example.springApi.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "cicles")
public class CiclesModel {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int cicle_id;
    private int organization_id;
    private LocalDate beggining_date;
    private LocalDate end_date;
    private String description;
    private float interest_rate;
    private float minimum_aportation;
    private LocalDate created_at;
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private OrganizationsModel organization;
    @Enumerated(EnumType.STRING)
    private Status status; // Status can be "open", "closed", etc.
    public enum Status {
        OPEN,
        CLOSED
    }
}
