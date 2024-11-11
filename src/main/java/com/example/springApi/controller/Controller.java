package com.example.springApi.controller;

import com.example.springApi.Model.User;
import com.example.springApi.Model.UserModel;
import com.example.springApi.Repositories.IUserRepository;
import com.example.springApi.Repositories.UserInterface;
import com.example.springApi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class Controller{
    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(){
        UserModel users = userRepository.findByUser("Ilan");
        return new ResponseEntity<>(users, org.springframework.http.HttpStatus.OK);
    }
}
