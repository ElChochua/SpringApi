package com.example.springApi.Dtos.organizationsDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrganizationDetailsDto {
    private int organization_id;
    private String organization_name;
    private int owner_id;
    private String description;
    private int total_members;
    private int total_transactions;
    private String created_at;
    private String status;
}
