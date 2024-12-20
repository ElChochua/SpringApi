package com.example.springApi.Dtos.organizationsDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OrganizationMemberDto {
    private int user_id;
    private int organization_id;
    private String name;
    private String user;
    private String status;
    private String email;
    private String role;
}
