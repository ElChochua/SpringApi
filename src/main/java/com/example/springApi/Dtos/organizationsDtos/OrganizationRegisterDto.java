package com.example.springApi.Dtos.organizationsDtos;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class OrganizationRegisterDto {
    String description;
    String organization_name;
    int owner_id;
}
