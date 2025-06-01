package com.example.springApi.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "organizations_members")
public class OrganizationsMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int organization_member_id;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private OrganizationsModel organization;
    @ManyToOne
    @JoinColumn(name ="user_id")
    private UserModel user;
    private String role;
    private Status status;
    private LocalDate joined_at;
    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
