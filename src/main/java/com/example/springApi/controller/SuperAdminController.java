package com.example.springApi.controller;

import com.example.springApi.Model.UserModel;
import com.example.springApi.Repositories.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@CrossOrigin
@RequestMapping("api/v1/superadmin")
public class SuperAdminController {
    @Autowired
    private UserRepository userRepository;
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
    @PostMapping("/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("Test");
    }
}
