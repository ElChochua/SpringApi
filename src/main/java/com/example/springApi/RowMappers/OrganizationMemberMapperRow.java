package com.example.springApi.RowMappers;

import com.example.springApi.Dtos.organizationsDtos.OrganizationMemberDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationMemberMapperRow implements RowMapper<OrganizationMemberDto> {
    @Override
    public OrganizationMemberDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrganizationMemberDto organizationMember = new OrganizationMemberDto();
        organizationMember.setOrganization_id(rs.getInt("organization_id"));
        organizationMember.setName(rs.getString("name"));
        organizationMember.setUser_id(rs.getInt("user_id"));
        organizationMember.setRole(rs.getString("role"));
        organizationMember.setStatus(rs.getString("status"));
        organizationMember.setEmail(rs.getString("email"));
        return organizationMember;
    }
}
