package com.example.springApi.controller;

import com.example.springApi.Model.UserModel;
import com.example.springApi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class Controller{
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
}
