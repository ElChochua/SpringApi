package com.example.springApi.Repositories;

import com.example.springApi.Dtos.UserCreditsDtos.UserCreditsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditsRepository implements ICreditsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void getCredits() {
        System.out.println("Getting credits");
    }
    UserCreditsDto getUsersCreditsByOrganizationId(int organizationId) {
        String Query = "SELECT * FROM credits WHERE organization_id = ?";
        return jdbcTemplate.queryForObject(Query, new Object[]{organizationId},
                new CreditsCustomMapperRow());
    }
}

class CreditsCustomMapperRow implements RowMapper<UserCreditsDto>{
    @Override
    public UserCreditsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserCreditsDto userCreditsDto = new UserCreditsDto();
            userCreditsDto.setCredit_ID(rs.getInt("credit_ID"));
            userCreditsDto.setUser_ID(rs.getInt("User_ID"));
            userCreditsDto.setOrganization_ID(rs.getInt("Organization_ID"));
            userCreditsDto.setCredit_score(rs.getInt("credit_score"));
            userCreditsDto.setCredit_limit(rs.getInt("credit_limit"));
            userCreditsDto.setAvailable_credit(rs.getInt("available_credit"));
            userCreditsDto.setUpdated_at(String.valueOf(rs.getDate("updated_at")));
            return  userCreditsDto;
    }
}