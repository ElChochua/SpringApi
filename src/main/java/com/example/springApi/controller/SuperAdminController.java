package com.example.springApi.controller;

import com.example.springApi.Dtos.UserCreditsDtos.UpdateUserRoleDto;
import com.example.springApi.Model.UserModel;
import com.example.springApi.Repositories.UserRepository;
import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Dtos.UsersDtos.UserDetailsDto;
import com.example.springApi.Dtos.UsersDtos.UsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("api/v1/superadmin")
public class SuperAdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel user){
        //TODO: Implementar lógica de registro
        try {
            userRepository.saveUser(user);
        }catch (Exception e){
            if(e.toString().contains("Duplicate entry")){
                return ResponseEntity.badRequest().body("El usuario ya existe");
            }
            return ResponseEntity.badRequest().body( e.toString() + "No se pudo registrar el usuario");
        }
        return ResponseEntity.ok(user.toString());
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers(){
        List<UsersDto> users = userRepository.getAllUsers();
        if(users.isEmpty()){
            return ResponseEntity.badRequest().body("No hay usuarios disponibles");
        }
        return ResponseEntity.ok(users);
    }
    @GetMapping("/get-all-users-details")
    public ResponseEntity<?> getAllUsersDetails(){
        List<UserDetailsDto> users = userRepository.getAllUsersDetails();
        if(users.isEmpty()){
            return ResponseEntity.badRequest().body("No hay usuarios disponibles");
        }
        return ResponseEntity.ok(users);
    }
    @GetMapping("/get-users-unassigned")
    public ResponseEntity<?> getUsers(){
        List<UsersDto> users = userRepository.getAllUnassignedUsers();
        if(users.isEmpty()){
            return ResponseEntity.badRequest().body("No hay usuarios disponibles");
        }
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/delete-user/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable int user_id){
        ResponseDto response = userRepository.deleteUserById(user_id);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @PutMapping("/delete-organization/{organization_id}")
    public ResponseEntity<?> deleteOrganization(@PathVariable int organization_id){
        ResponseDto response = organizationRepository.deleteOrganization(organization_id);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @PutMapping("/update-user-role")
    public ResponseEntity<?> updateUserRole(@RequestBody UpdateUserRoleDto user){
        userRepository.updateUserRole(user);
        return ResponseEntity.ok(user);
    }

}
