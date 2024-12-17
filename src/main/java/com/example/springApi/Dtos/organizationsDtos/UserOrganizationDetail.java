package com.example.springApi.Dtos.organizationsDtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserOrganizationDetail {
    private int member_id;
    private int user_id;
    private int organization_id;
    private String role;
}
