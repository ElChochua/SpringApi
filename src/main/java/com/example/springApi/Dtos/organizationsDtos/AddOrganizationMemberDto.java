package com.example.springApi.Dtos.organizationsDtos;

import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AddOrganizationMemberDto {
    private int user_id;
    private int organization_id;
    private String role;
}
