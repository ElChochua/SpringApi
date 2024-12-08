package com.example.springApi.Repositories;

import com.example.springApi.Model.UserModel;
import com.example.springApi.dto.authDto.RegisterDto;
import com.example.springApi.dto.authDto.UsersDtos.UsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserModel findByUser(String user) {
        String Query = "SELECT * FROM Users WHERE User = ?";
        return jdbcTemplate.queryForObject(Query,new Object[]{user},
                new BeanPropertyRowMapper<>(UserModel.class));
    }

    public UserModel findByEmail(String email) {
        String Query = "SELECT * FROM Users WHERE Email = ?";
        return jdbcTemplate.queryForObject(Query,new Object[]{email},
                new BeanPropertyRowMapper<>(UserModel.class));
    }
    public UsersDto findByUserName(String username){
        String Query = "SELECT user_id, user, email, role FROM users WHERE user = ?";
        return jdbcTemplate.queryForObject(Query, new Object[]{username}, new CustomMapperRow());
    }
    public List<UsersDto> getAllUsers(){
        String Query = "SELECT user_id, user, email, role FROM users where role <> 'SUPER_ADMIN'";

        return jdbcTemplate.query(Query, new CustomMapperRow());
    }
    public void saveUser(UserModel user){
        String Query = "INSERT INTO users (User, Email, Password, Role) VALUES (?,?,?,?)";
        String passwordEncoded = this.passwordEncoder.encode(user.getPassword());
        jdbcTemplate.update(Query, user.getUser(), user.getEmail(), passwordEncoded, user.getRole());
    }
    public void selfRegisterUser(RegisterDto user){
        String Query = "INSERT INTO users (User, Email, Password) VALUES (?,?,?)";
        String passwordEncoded = this.passwordEncoder.encode(user.getPassword());
        jdbcTemplate.update(Query, user.getUsername(), user.getEmail(), passwordEncoded);
    }
    public List<UsersDto> getAllUnassignedUsers(){
        String Query = "SELECT user_id, user, email, role FROM users WHERE role IS NULL";
        return jdbcTemplate.query(Query, new CustomMapperRow());
    }
}
class CustomMapperRow implements RowMapper<UsersDto>{
    @Override
    public UsersDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        UsersDto user = new UsersDto();
        user.setUser_id(rs.getInt("user_id"));
        user.setUser(rs.getString("user"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        return user;
    }
}