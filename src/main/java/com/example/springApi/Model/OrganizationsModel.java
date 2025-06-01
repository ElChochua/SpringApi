package com.example.springApi.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "organizations")
public class OrganizationsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int organization_id;
    private int owner_id;
    private String organization_name;
    private String description;
    private String invitation_code;
    @Enumerated(EnumType.STRING)
    private  OrganizationStatus status; // Status can be "active", "inactive", etc.
    @OneToMany(mappedBy = "organization_id", cascade = CascadeType.ALL)
    private List<CiclesModel> cicles = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "actual_cicle_id")
    private CiclesModel actual_cicle;
    @OneToMany(mappedBy = "organization")
    private List<OrganizationsMember> members;
    public enum OrganizationStatus {
        ACTIVE,
        INACTIVE
    }
}
