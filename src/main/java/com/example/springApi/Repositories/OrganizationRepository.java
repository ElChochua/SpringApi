package com.example.springApi.Repositories;
import com.example.springApi.dto.authDto.ResponseDto;
import com.example.springApi.dto.authDto.UsersDtos.UsersDto;
import com.example.springApi.dto.authDto.organizationsDtos.OrganizationDetailsDto;
import com.example.springApi.dto.authDto.organizationsDtos.OrganizationRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrganizationRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ResponseDto registerOrganization(OrganizationRegisterDto organization) {
        {
            try {
                String Query = "INSERT INTO organizations(organization_name, owner_id, description) VALUES (?,?,?)";
                jdbcTemplate.update(Query, organization.getOrganization_name(), organization.getOwner_id(), organization.getDescription());
            } catch (Exception e) {
                if(e.getMessage().contains("Duplicate entry")){
                    return new ResponseDto("Organization already exists",409);
                }else{
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
    public List<OrganizationDetailsDto> getAllOrganizations(){
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
    public ResponseDto addUserToOrganization(int organization_id, int user_id){
        try {
            String Query = "INSERT INTO organization_members(organization_id, user_id) VALUES (?,?)";
            jdbcTemplate.update(Query, organization_id, user_id);
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

}