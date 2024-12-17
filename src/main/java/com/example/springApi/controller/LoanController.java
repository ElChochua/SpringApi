package com.example.springApi.controller;

import com.example.springApi.Dtos.LoanDtos.LoanDto;
import com.example.springApi.Repositories.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/v1/loan")
public class LoanController {
    private static final Logger loger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/get-loans-by-user/{user_id}")
    public ResponseEntity<?> getLoansByUser(@PathVariable("user_id") int userId){
        List<LoanDto> loans = loanRepository.getLoansByApplicant(userId);
        if(loans.isEmpty()){
            return ResponseEntity.badRequest().body("No loans available");
        }
        return ResponseEntity.ok(loans);
    }
    @GetMapping("/get-loans-by-id/{loan_id}")
    public ResponseEntity<?> getLoansById(@PathVariable("loan_id") int loanId){
        List<LoanDto> loans = loanRepository.getLoansByApplicant(loanId);
        if(loans.isEmpty()){
            return ResponseEntity.badRequest().body("No loans available");
        }
        return ResponseEntity.ok(loans);
    }
    @GetMapping("/get-loans-by-status/{status}")
    public ResponseEntity<?> getLoansByStatus(@PathVariable("status") String status){
        List<LoanDto> loans = loanRepository.getLoansByStatus(status);
        if(loans.isEmpty()){
            return ResponseEntity.badRequest().body("No loans available");
        }
        return ResponseEntity.ok(loans);
    }
    @GetMapping("/get-all-loans")
    public ResponseEntity<?> getAllLoans(){
        List<LoanDto> loans = loanRepository.getAllLoans();
        if(loans.isEmpty()){
            return ResponseEntity.badRequest().body("No loans available");
        }
        return ResponseEntity.ok(loans);
    }
    @PostMapping("/register-loan")
    public ResponseEntity<?> registerLoan(@RequestBody LoanDto loan){
        return ResponseEntity.ok(loanRepository.registerLoan(loan));
    }
    @PostMapping("/approve-loan/{loan_id}")
    public ResponseEntity<?> approveLoan(@PathVariable("loan_id") int loanId){
        return ResponseEntity.ok(loanRepository.approveLoan(loanId));
    }
    @PostMapping("/reject-loan/{loan_id}")
    public ResponseEntity<?> rejectLoan(@PathVariable("loan_id") int loanId){
        return ResponseEntity.ok(loanRepository.rejectLoan(loanId));
    }
    @GetMapping("/get-loan-by-id/{loan_id}")
    public ResponseEntity<?> getLoanById(@PathVariable("loan_id") int loanId){
        LoanDto loan = loanRepository.getLoanById(loanId);
        if(loan == null){
            return ResponseEntity.badRequest().body("No loan available");
        }
        return ResponseEntity.ok(loan);
    }
    @GetMapping("/get-all-active-loans-by-member/{user_id}")
    public ResponseEntity<?> getAllActiveLoansByMember(@PathVariable("user_id") int userId){
        List<LoanDto> loans = loanRepository.getAllActiveLoansByMember(userId);
        if(loans.isEmpty()){
            return ResponseEntity.badRequest().body("No loans available");
        }
        return ResponseEntity.ok(loans);
    }
    @GetMapping("/get-all-inactive-loans-by-member/{user_id}")
    public ResponseEntity<?> getAllInactiveLoansByMember(@PathVariable("user_id") int userId){
        List<LoanDto> loans = loanRepository.getAllInactiveLoansByMember(userId);
        if(loans.isEmpty()){
            return ResponseEntity.badRequest().body("No loans available");
        }
        return ResponseEntity.ok(loans);
    }
    @GetMapping("/get-all-rejected-loans-by-member/{user_id}")
    public ResponseEntity<?> getAllRejectedLoansByMember(@PathVariable("user_id") int userId){
        List<LoanDto> loans = loanRepository.getAllRejectedLoansByMember(userId);
        if(loans.isEmpty()){
            return ResponseEntity.badRequest().body("No loans available");
        }
        return ResponseEntity.ok(loans);
    }
    @GetMapping("/get-all-approved-loans-by-member/{user_id}")
    public ResponseEntity<?> getAllApprovedLoansByMember(@PathVariable("user_id") int userId){
        List<LoanDto> loans = loanRepository.getAllApprovedLoansByMember(userId);
        if(loans.isEmpty()){
            return ResponseEntity.badRequest().body("No loans available");
        }
        return ResponseEntity.ok(loans);
    }
    @PostMapping("/loan-apply")
    public ResponseEntity<?> loanApply(@RequestBody LoanDto loan){
        return ResponseEntity.ok(loanRepository.registerLoan(loan));
    }

}
