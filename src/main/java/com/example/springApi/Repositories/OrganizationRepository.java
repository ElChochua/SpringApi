package com.example.springApi.Repositories;
import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Dtos.UsersDtos.UserDetailDto;
import com.example.springApi.Dtos.organizationsDtos.*;
import com.example.springApi.RowMappers.OrganizationMemberMapperRow;
import com.example.springApi.RowMappers.UserCustomDetailMapperRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrganizationRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ResponseDto registerOrganization(OrganizationRegisterDto organization) {
        {
            try {
                String Query = "INSERT INTO organizations(organization_name, owner_ID, description) VALUES (?,?,?)";
                jdbcTemplate.update(Query, organization.getOrganization_name(), organization.getOwner_id(), organization.getDescription());
            } catch (Exception e) {
                if(e.getMessage().contains("Duplicate entry")){
                    return new ResponseDto("Organization already exists",400);
                }else{
                    System.out.println(e);
                    return new ResponseDto("Organization could not be registered Error: " + e.toString(), 500);
                }
            }
            return new ResponseDto("Organization registered successfully",200);
        }
    }
    public List<OrganizationDetailsDto> getAllOrganizationActives(){
        String Query = "SELECT * FROM organizations_details WHERE status = 'active'";
        RowMapper<OrganizationDetailsDto> rowMapper = (rs, rowNum)->{
            OrganizationDetailsDto organization = new OrganizationDetailsDto();
            organization.setOrganization_id(rs.getInt("organization_id"));
            organization.setOrganization_name(rs.getString("organization_name"));
            organization.setOwner_id(rs.getInt("owner_id"));
            organization.setDescription(rs.getString("description"));
            organization.setTotal_members(rs.getInt("total_members"));
            organization.setTotal_transactions(rs.getInt("total_transactions"));
            organization.setCreated_at(rs.getString("created_at"));
            organization.setStatus(rs.getString("status"));
            return organization;
        };
        return jdbcTemplate.query(Query, rowMapper);
    }
    public List<OrganizationDetailsDto> getAllOrganizationInactives(){
        String Query = "SELECT * FROM organizations_details WHERE status = 'inactive'";
        RowMapper<OrganizationDetailsDto> rowMapper = (rs, rowNum)->{
            OrganizationDetailsDto organization = new OrganizationDetailsDto();
            organization.setOrganization_id(rs.getInt("organization_id"));
            organization.setOrganization_name(rs.getString("organization_name"));
            organization.setOwner_id(rs.getInt("owner_id"));
            organization.setDescription(rs.getString("description"));
            organization.setTotal_members(rs.getInt("total_members"));
            organization.setTotal_transactions(rs.getInt("total_transactions"));
            organization.setCreated_at(rs.getString("created_at"));
            organization.setStatus(rs.getString("status"));
            return organization;
        };
        return jdbcTemplate.query(Query, rowMapper);
    }
    //get all organizations by owner
    public List<OrganizationDetailsDto> getAllOrganizationByOwner(int owner_id){
        String Query = "SELECT * FROM organizations_details WHERE owner_id ='" +owner_id +"';";
        RowMapper<OrganizationDetailsDto> rowMapper = (rs, rowNum)->{
            OrganizationDetailsDto organization = new OrganizationDetailsDto();
            organization.setOrganization_id(rs.getInt("organization_id"));
            organization.setOrganization_name(rs.getString("organization_name"));
            organization.setOwner_id(rs.getInt("owner_id"));
            organization.setDescription(rs.getString("description"));
            organization.setTotal_members(rs.getInt("total_members"));
            organization.setTotal_transactions(rs.getInt("total_transactions"));
            organization.setCreated_at(rs.getString("created_at"));
            organization.setStatus(rs.getString("status"));
            return organization;
        };
        return jdbcTemplate.query(Query, rowMapper);
    }
    public List<OrganizationDetailsDto> getAllOrganizationsByUser(int user_id){
        String Query = "SELECT * FROM organizations_details WHERE organization_id IN (SELECT organization_id FROM organizations_members WHERE user_id = ?)\n";
        RowMapper<OrganizationDetailsDto> rowMapper = (rs, rowNum)->{
            OrganizationDetailsDto organization = new OrganizationDetailsDto();
            organization.setOrganization_id(rs.getInt("organization_id"));
            organization.setOrganization_name(rs.getString("organization_name"));
            organization.setOwner_id(rs.getInt("owner_id"));
            organization.setDescription(rs.getString("description"));
            organization.setTotal_members(rs.getInt("total_members"));
            organization.setTotal_transactions(rs.getInt("total_transactions"));
            organization.setCreated_at(rs.getString("created_at"));
            organization.setStatus(rs.getString("status"));
            return organization;
        };
        return jdbcTemplate.query(Query, rowMapper, user_id);
    }
    public List<OrganizationDetailsDto> getAllOrganizationsDetails(){
        String Query = "SELECT * FROM organizations_details";
        RowMapper<OrganizationDetailsDto> rowMapper = (rs, rowNum)->{
            OrganizationDetailsDto organization = new OrganizationDetailsDto();
            organization.setOrganization_id(rs.getInt("organization_id"));
            organization.setOrganization_name(rs.getString("organization_name"));
            organization.setOwner_id(rs.getInt("owner_id"));
            organization.setDescription(rs.getString("description"));
            organization.setTotal_members(rs.getInt("total_members"));
            organization.setTotal_transactions(rs.getInt("total_transactions"));
            organization.setCreated_at(rs.getString("created_at"));
            organization.setStatus(rs.getString("status"));
            return organization;
        };
        return jdbcTemplate.query(Query, rowMapper);
    }
    public List<OrganizationDto> getAllOrganizations(){
        String Query = "SELECT * FROM organizations";
        RowMapper<OrganizationDto> rowMapper = (rs, rowNum)->{
            OrganizationDto organization = new OrganizationDto();
                organization.setOrganization_id(rs.getInt("organization_id"));
                organization.setOrganization_name(rs.getString("organization_name"));
                organization.setOwner_id(rs.getInt("owner_id"));
                organization.setDescription(rs.getString("description"));
                return organization;
            };
        return jdbcTemplate.query(Query, rowMapper);
    }

    public ResponseDto addUserToOrganization(AddOrganizationMemberDto addOrganizationMemberDto){
        try {
            System.out.println(addOrganizationMemberDto.getOrganization_id() + " " + addOrganizationMemberDto.getUser_id());
            if(addOrganizationMemberDto.getRole() == null){
                addOrganizationMemberDto.setRole("member");
            }

            System.out.println(addOrganizationMemberDto.getRole() + " " + addOrganizationMemberDto.getOrganization_id() + " " + addOrganizationMemberDto.getUser_id());
            String Query = "INSERT INTO organizations_members(organization_ID, User_ID) VALUES (?,?)";
            jdbcTemplate.update(Query, addOrganizationMemberDto.getOrganization_id(), addOrganizationMemberDto.getUser_id());

            updateTotalMembers(addOrganizationMemberDto.getOrganization_id());
        } catch (Exception e) {
            return new ResponseDto("User could not be added to organization Error: " + e.toString(), 500);
        }
        return new ResponseDto("User added to organization",200);
    }
    public ResponseDto updateOrganizationStatus(int organization_id, String status){
        try {
            String Query = "UPDATE organizations_details SET status = ? WHERE organization_id = ?";
            jdbcTemplate.update(Query, status, organization_id);
        } catch (Exception e) {
            return new ResponseDto("Organization status could not be updated Error: " + e.toString(), 500);
        }
        return new ResponseDto("Organization status updated successfully",200);
    }
    public ResponseDto updateOrganizationDetails(OrganizationRegisterDto organization){
        try {
            String Query = "UPDATE organizations SET organization_name = ?, description = ? WHERE organization_id = ?";
            jdbcTemplate.update(Query, organization.getOrganization_name(), organization.getDescription(), organization.getOwner_id());
        } catch (Exception e) {
            return new ResponseDto("Organization details could not be updated Error: " + e.toString(), 500);
        }
        return new ResponseDto("Organization details updated successfully",200);
    }
    public ResponseDto deleteOrganization(int organization_id){
        try {
            System.out.println(organization_id);
            String Query = "DELETE FROM organizations WHERE organization_ID = ?";
            jdbcTemplate.update(Query, organization_id);
        } catch (Exception e) {
            return new ResponseDto("Organization could not be deleted Error: " + e.toString(), 500);
        }
        return new ResponseDto("Organization deleted successfully",200);
    }
    public ResponseDto deleteUserFromOrganization(int organization_id, int user_id){
        try {
            String Query = "DELETE FROM organizations_members WHERE organization_id = ? AND user_id = ? ";
            jdbcTemplate.update(Query, organization_id, user_id);
            updateTotalMembers(organization_id);
        } catch (Exception e) {
            return new ResponseDto("User could not be deleted from organization Error: " + e.toString(), 500);
        }
        return new ResponseDto("User deleted from organization",200);
    }
    public ResponseDto updateMemberRole(OrganizationMemberDto organizationMember){
        String Query = "UPDATE organizations_members SET role = ? WHERE organization_id = ? AND user_id = ?";
        try {
            jdbcTemplate.update(Query, organizationMember.getRole().toLowerCase(), organizationMember.getOrganization_id(), organizationMember.getUser_id());
        } catch (Exception e) {
            return new ResponseDto("Role could not be updated Error: " + e.toString(), 500);
        }
        return new ResponseDto("Role updated successfully",200);
    }
    public ResponseDto deleteMember(OrganizationMemberDto organizationMember){
        String Query = "DELETE FROM organizations_members WHERE organization_id = ? AND user_id = ?";
        try {
            updateTotalMembers(organizationMember.getOrganization_id());
            jdbcTemplate.update(Query, organizationMember.getOrganization_id(), organizationMember.getUser_id());
        } catch (Exception e) {
            return new ResponseDto("Member could not be deleted Error: " + e.toString(), 500);
        }
        return new ResponseDto("Member deleted successfully",200);
    }
    public ResponseDto removeMemberFromOrganization(int organization_id, int user_id){
        try {
        String Query = "DELETE FROM organizations_members WHERE organization_id = ? AND user_id = ?";
            jdbcTemplate.update(Query, organization_id, user_id);
        } catch (Exception e) {
            return new ResponseDto("Member could not be deleted Error: " + e.toString(), 500);
        }
        updateTotalMembers(organization_id);
        return new ResponseDto("Member deleted successfully",200);
    }
    public List<OrganizationMemberDto> getAllOrganizationMembers(int organization_id){
        String Query ="WITH RankedUsers AS (\n" +
                "    SELECT\n" +
                "        ud.user_id,\n" +
                "        ud.name,\n" +
                "        ud.email,\n" +
                "        om.role,\n" +
                "        om.status,\n" +
                "        om.organization_ID,\n" +
                "        ROW_NUMBER() OVER (PARTITION BY ud.user_id ORDER BY om.role ASC) AS row_num\n" +
                "    FROM user_detail ud\n" +
                "             INNER JOIN organizations_members om ON ud.user_id = om.User_ID\n" +
                "    WHERE om.organization_ID = ?\n" +
                ")\n" +
                "SELECT user_id, name, email, role, status, organization_ID\n" +
                "FROM RankedUsers\n" +
                "WHERE row_num = 1;\n";
        return jdbcTemplate.query(Query, new OrganizationMemberMapperRow(), organization_id);
    }
    public List<OrganizationMemberDto> getAllUsersOutOfOrganization(int organization_id){
        String Query ="WITH RankedUsers AS (\n" +
                "    SELECT\n" +
                "        ud.user_id,\n" +
                "        ud.name,\n" +
                "        ud.email,\n" +
                "        om.role,\n" +
                "        om.status,\n" +
                "        om.organization_ID,\n" +
                "        ROW_NUMBER() OVER (PARTITION BY ud.user_id ORDER BY om.role ASC) AS row_num\n" +
                "    FROM user_detail ud\n" +
                "             LEFT JOIN organizations_members om ON ud.user_id = om.User_ID\n" +
                "    WHERE om.organization_ID IS NULL OR om.organization_ID <> ?\n" +
                ")\n" +
                "SELECT user_id, name, email, role, status, organization_ID\n" +
                "FROM RankedUsers\n" +
                "WHERE row_num = 1\n" +
                "\n" +
                "UNION\n" +
                "\n" +
                "SELECT\n" +
                "    ud.user_id,\n" +
                "    ud.name,\n" +
                "    ud.email,\n" +
                "    NULL as role,\n" +
                "    NULL as status,\n" +
                "    NULL as organization_ID\n" +
                "FROM user_detail ud\n" +
                "         LEFT JOIN organizations_members om ON ud.user_id = om.User_ID\n" +
                "WHERE om.User_ID IS NULL";

        return jdbcTemplate.query(Query, new OrganizationMemberMapperRow(), organization_id);
}
    public void updateTotalMembers(int organization_id){
        String Query = "call update_members_count(?)";
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("update_members_count");
        Map<String, Object> param = new HashMap<>();
        param.put("org_id", organization_id);
        simpleJdbcCall.execute(param);
        jdbcTemplate.update(Query, organization_id);
    }
}
