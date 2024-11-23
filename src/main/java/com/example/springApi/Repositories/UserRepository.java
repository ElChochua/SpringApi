package com.example.springApi.Repositories;

import com.example.springApi.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
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
    @Override
    public UserModel findByEmail(String email) {
        String Query = "SELECT * FROM Users WHERE Email = ?";
        return jdbcTemplate.queryForObject(Query,new Object[]{email},
                new BeanPropertyRowMapper<>(UserModel.class));
    }
    public void saveUser(UserModel user){
        String Query = "INSERT INTO users (User, Email, Password, Role) VALUES (?,?,?,?)";
        String passwordEncoded = this.passwordEncoder.encode(user.getPassword());
        jdbcTemplate.update(Query, user.getUser(), user.getEmail(), passwordEncoded, user.getRole());
    }

}
