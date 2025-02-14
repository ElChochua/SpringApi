package com.example.springApi.Repositories;

import com.example.springApi.Dtos.UserCreditsDtos.UpdateUserRoleDto;
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
        System.out.println(passwordEncoded);
        jdbcTemplate.update(Query, user.getUsername(), user.getEmail(), passwordEncoded);
    }
    public List<UsersDto> getAllUnassignedUsers(){
        String Query = "SELECT user_id, user, email, role FROM users WHERE role IS NULL";
        return jdbcTemplate.query(Query, new UserCustomMapperRow());
    }
    public List<UserDetailDto> getAllUsersDetails(){
        String Query = "SELECT \n" +
                "    ud.user_id, ud.user_name, ud.name, ud.last_name, ud.birthdate, \n" +
                "    ud.curp, ud.phone_number, ud.email, ud.status, ud.created_at, \n" +
                "    u.role\n" +
                "FROM user_detail ud\n" +
                "LEFT JOIN users u ON ud.user_id = u.User_ID\n" +
                "WHERE u.role IS NULL OR u.role != 'SUPER_ADMIN'";
        return jdbcTemplate.query(Query, new UserCustomDetailMapperRow());
    }
    public UserDetailDto getUserById(int id){
        String Query = "Select * from user_detail where user_id = ?";
        return jdbcTemplate.queryForObject(Query, new Object[]{id}, new UserCustomDetailMapperRowWhitOutRole());
    }
    public ResponseDto updateUser(UserDetailDto user){
        String Query = "UPDATE user_detail SET name = ?, last_name = ?, birthdate = ?, curp = ?, phone_number = ? WHERE user_id = ?";
        try {
            jdbcTemplate.update(Query, user.getName(), user.getLast_name(), user.getBirthdate(), user.getCurp(), user.getPhone_number(), user.getUser_id());
        }catch (Exception e){
            return new ResponseDto("No se pudo actualizar el usuario: " + e, 500);
        }
        return new ResponseDto("Usuario actualizado correctamente", 200);
    }
    public ResponseDto updateUserRole(UpdateUserRoleDto user){
        String Query = "update users set role = '"+user.getRole()+"' where User_ID = ?";
        try{
            jdbcTemplate.update(Query, user.getUser_id());
        }catch (Exception e){
            return new ResponseDto("No se pudo actualizar el rol del usuario " + e, 500);
        }
            return new ResponseDto("Rol actualizado correctamente", 200);
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
class UserCustomDetailMapperRowWhitOutRole implements RowMapper<UserDetailDto>{
    @Override
    public UserDetailDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDetailDto user = new UserDetailDto();
        user.setUser_id(rs.getInt("user_id"));
        user.setUser_name(rs.getString("user_name"));
        user.setName(rs.getString("name"));
        user.setLast_name(rs.getString("last_name"));
        user.setBirthdate(String.valueOf(rs.getDate("birthdate")));
        user.setCurp(rs.getString("curp"));
        user.setPhone_number(rs.getString("phone_number"));
        user.setEmail(rs.getString("email"));
        user.setStatus(rs.getString("status"));
        user.setCreated_at(String.valueOf(rs.getDate("created_at")));
        return user;
    }
}