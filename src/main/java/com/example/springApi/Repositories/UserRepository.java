package com.example.springApi.Repositories;

import com.example.springApi.Model.UserModel;
import com.example.springApi.Dtos.RegisterDto;
import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Dtos.UsersDtos.UserDetailDto;
import com.example.springApi.Dtos.UsersDtos.UsersDto;
import com.example.springApi.RowMappers.UserCustomDetailMapperRow;
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
    public ResponseDto deleteUserById(int id){
        try {
            String Query = "DELETE FROM users WHERE user_id = ?";
            jdbcTemplate.update(Query, id);
        }catch (Exception e){
            return new ResponseDto("No se pudo eliminar el usuario", 500);
        }
        return new ResponseDto("Usuario eliminado correctamente", 200);
    }
    public UserModel findByEmail(String email) {
        String Query = "SELECT * FROM Users WHERE Email = ?";
        return jdbcTemplate.queryForObject(Query,new Object[]{email},
                new BeanPropertyRowMapper<>(UserModel.class));
    }
    public UsersDto findByUserName(String username){
        String Query = "SELECT user_id, user, email, role FROM users WHERE user = ?";
        return jdbcTemplate.queryForObject(Query, new Object[]{username}, new UserCustomMapperRow());
    }
    public List<UsersDto> getAllUsers(){
        String Query = "SELECT user_id, user, email, role FROM users where role <> 'SUPER_ADMIN'";
        return jdbcTemplate.query(Query, new UserCustomMapperRow());
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
        return jdbcTemplate.query(Query, new UserCustomMapperRow());
    }
    public List<UserDetailDto> getAllUsersDetails(){
        String Query = "SELECT ud.user_id, ud.user_name,ud.name,ud.last_name,ud.birthdate,ud.curp,ud.phone_number, ud.email, ud.status, ud.created_at, u.role from  user_detail ud join users u on ud.user_id = u.User_ID AND u.role != 'SUPER_ADMIN'";
        return jdbcTemplate.query(Query, new UserCustomDetailMapperRow());
    }
}
//Mapper para el usuario
class UserCustomMapperRow implements RowMapper<UsersDto>{
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
