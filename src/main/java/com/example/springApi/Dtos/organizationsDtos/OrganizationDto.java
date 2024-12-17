package com.example.springApi.Dtos.organizationsDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OrganizationDto {
    private int organization_id;
    private String organization_name;
    private int owner_id;
    private String description;
}
