package com.example.springApi.RowMappers;

import com.example.springApi.Dtos.UsersDtos.UserDetailsDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCustomDetailMapperRow implements RowMapper<UserDetailsDto> {
    @Override
    public UserDetailsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDetailsDto user = new UserDetailsDto();
        user.setUser_id(rs.getInt("user_id"));
        user.setUser_name(rs.getString("user_name"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setLast_name(rs.getString("last_name"));
        user.setBirthdate(rs.getString("birthdate"));
        user.setCurp(rs.getString("curp"));
        user.setStatus(rs.getString("status"));
        user.setRole(rs.getString("role"));
        user.setCreated_at(rs.getString("created_at"));
        user.setPhone_number(rs.getString("phone_number"));
        return user;
    }
}