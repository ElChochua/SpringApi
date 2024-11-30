package com.example.springApi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/borrower")
public class BorrowerController {
    @PostMapping("/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("Test");
    }
}