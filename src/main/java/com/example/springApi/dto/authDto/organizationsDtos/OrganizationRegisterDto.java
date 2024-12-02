package com.example.springApi.dto.authDto.organizationsDtos;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class OrganizationRegisterDto {
    String organization_name;
    int owner_id;
    String description;
}
