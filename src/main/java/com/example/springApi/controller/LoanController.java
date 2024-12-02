package com.example.springApi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/loan")
public class LoanController {
    @PostMapping("/test")
    public String registerLoan(){
        return "Loan registered";
    }
}
