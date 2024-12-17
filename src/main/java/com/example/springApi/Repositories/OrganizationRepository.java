package com.example.springApi.Repositories;
import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Dtos.UsersDtos.UserDetailDto;
import com.example.springApi.Dtos.UsersDtos.UsersDto;
import com.example.springApi.Dtos.organizationsDtos.OrganizationDetailsDto;
import com.example.springApi.Dtos.organizationsDtos.OrganizationDto;
import com.example.springApi.Dtos.organizationsDtos.OrganizationMemberDto;
import com.example.springApi.Dtos.organizationsDtos.OrganizationRegisterDto;
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
    public ResponseDto addUserToOrganization(OrganizationMemberDto organizationMember){
        try {
            String Query = "INSERT INTO organization_members(organization_id, user_id) VALUES (?,?,?)";
            jdbcTemplate.update(Query, organizationMember.getOrganization_id(), organizationMember.getUser_id(), organizationMember.getRole());
            updateTotalMembers(organizationMember.getOrganization_id());
        } catch (Exception e) {
            return new ResponseDto("User could not be added to organization Error: " + e.toString(), 500);
        }
        return new ResponseDto("User added to organization",200);
    }
    public ResponseDto updateOrganizationStatus(int organization_id, String status){
        try {
            String Query = "UPDATE organizations SET status = ? WHERE organization_id = ?";
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
            String Query = "DELETE FROM organizations WHERE organization_id = ?";
            jdbcTemplate.update(Query, organization_id);
        } catch (Exception e) {
            return new ResponseDto("Organization could not be deleted Error: " + e.toString(), 500);
        }
        return new ResponseDto("Organization deleted successfully",200);
    }
    public ResponseDto deleteUserFromOrganization(int organization_id, int user_id){
        try {
            String Query = "DELETE FROM organizations_members WHERE organization_id = ? AND user_id = ? ";
            updateTotalMembers(organization_id);
            jdbcTemplate.update(Query, organization_id, user_id);

        } catch (Exception e) {
            return new ResponseDto("User could not be deleted from organization Error: " + e.toString(), 500);
        }
        return new ResponseDto("User deleted from organization",200);
    }
    public List<UserDetailDto> getAllUsersOutOfOrganization(int organization_id){
        String Query = "SELECT * FROM user_detail WHERE user_id NOT IN (SELECT user_id FROM organizations_members WHERE organization_id = ?)";
        return jdbcTemplate.query(Query, new UserDetailCustomRowMapper(), organization_id);
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
        String Query = "DELETE FROM organizations_members WHERE organization_id = ? AND user_id = ?";
        try {
            updateTotalMembers(organization_id);
            jdbcTemplate.update(Query, organization_id, user_id);
        } catch (Exception e) {
            return new ResponseDto("Member could not be deleted Error: " + e.toString(), 500);
        }
        return new ResponseDto("Member deleted successfully",200);
    }
    public List<UserDetailDto> getAllOrganizationMembers(int organization_id){
        String Query = "SELECT * FROM organizations_members WHERE organization_id = ?";
        return jdbcTemplate.query(Query, new UserDetailCustomRowMapper(), organization_id);
    }
    public void updateTotalMembers(int organization_id){
        String Query = "call update_total_members(?)";
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("update_members_count");
        Map<String, Object> param = new HashMap<>();
        param.put("org_id", organization_id);
        simpleJdbcCall.execute(param);
        jdbcTemplate.update(Query, organization_id);
    }
}
class UserDetailCustomRowMapper implements RowMapper<UserDetailDto>{
    @Override
    public UserDetailDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDetailDto user = new UserDetailDto();
        user.setUser_id(rs.getInt("user_id"));
        user.setUser_name(rs.getString("user_name"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setLast_name(rs.getString("last_name"));
        user.setBirthdate(rs.getString("birthdate"));
        user.setStatus(rs.getString("status"));
        user.setCreated_at(rs.getString("created_at"));
        user.setPhone_number(rs.getString("phone_number"));
        return user;
    }
}