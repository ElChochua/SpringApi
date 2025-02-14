package com.example.springApi.controller;

import com.example.springApi.Dtos.LoanDtos.LoanDto;
import com.example.springApi.Dtos.LoanDtos.PaymentDto;
import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Dtos.UserCreditsDtos.UserCreditsDto;
import com.example.springApi.Repositories.LoanRepository;
import org.apache.coyote.Response;
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
    /*
    @GetMapping("/get-loans-by-id/{loan_id}")
    public ResponseEntity<?> getLoansById(@PathVariable("loan_id") int loanId){
        List<LoanDto> loans = loanRepository.getLoansByLoanID(loanId);
        if(loans.isEmpty()){
            return ResponseEntity.badRequest().body("No loans available");
        }
        return ResponseEntity.ok(loans);
    }
     */
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
        System.out.println(loanId);
        ResponseDto response = loanRepository.rejectLoan(loanId);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
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
    @GetMapping("/get-all-active-loans/")
    public ResponseEntity<?> getAllActiveLoans(){
        List<LoanDto> loans = loanRepository.getAllActiveLoans();
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
        System.out.println(userId);
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
        ResponseDto response = loanRepository.registerLoan(loan);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-all-credits")
    public ResponseEntity<?> getAllCredits(){
        List<UserCreditsDto> credits = loanRepository.getAllCredits();
        if(credits.isEmpty()){
            return ResponseEntity.badRequest().body("No loans available");
        }
        return ResponseEntity.ok(credits);
    }
    @GetMapping("/get-loans-by-organization/{organization_id}")
    public ResponseEntity<?> getLoansByOrganization(@PathVariable("organization_id") int organizationId){
        List<LoanDto> loans = loanRepository.getLoansByOrganization(organizationId);
        if(loans.isEmpty()){
            return ResponseEntity.badRequest().body("No loans available");
        }
        return ResponseEntity.ok(loans);
    }
    @GetMapping("/get-all-credits-by-user/{user_id}")
    public ResponseEntity<?> getAllCreditsByUser(@PathVariable("user_id") int userId){
        List<UserCreditsDto> credits = loanRepository.getAllCreditsByUser(userId);
        if(credits.isEmpty()){
            return ResponseEntity.badRequest().body("No loans available");
        }
        return ResponseEntity.ok(credits);
    }
    @GetMapping("/get-all-credits-by-organization/{organization_id}")
    public ResponseEntity<?> getAllCreditsByOrganization(@PathVariable("organization_id") int organizationId){
        List<UserCreditsDto> credits = loanRepository.getAllCreditsByOrganizationId(organizationId);
        if(credits.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseDto("No hay creditos en esta organiacion", 404));
        }
        return ResponseEntity.ok(credits);
    }
    @PostMapping("/make-payment/")
    public ResponseEntity<?> makePayment(@RequestBody PaymentDto payment){
        ResponseDto response = loanRepository.makePayment(payment);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @PutMapping("/update-loan")
    public ResponseEntity<?> updateLoan(@RequestBody LoanDto loan){

        ResponseDto response = loanRepository.updateLoan(loan);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

}
