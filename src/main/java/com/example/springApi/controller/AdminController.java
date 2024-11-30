package com.example.springApi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("api/v1/admin")
public class AdminController {
    @PostMapping("/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("Test");
    }
}
