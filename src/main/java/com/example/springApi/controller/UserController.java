package com.example.springApi.controller;

import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Dtos.UsersDtos.UserDetailsDto;
import com.example.springApi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("Test");
    }
    @GetMapping("/get-user-by-id/{user_id}")
    public ResponseEntity<?> getUserById(@PathVariable("user_id") int user_id){
        UserDetailsDto user = userRepository.getUserById(user_id);
        if(user == null){
            return ResponseEntity.badRequest().body(new ResponseDto("No user found", 404));
        }
        return ResponseEntity.ok(user);
    }
    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody UserDetailsDto user){
        ResponseDto response = userRepository.updateUser(user);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
