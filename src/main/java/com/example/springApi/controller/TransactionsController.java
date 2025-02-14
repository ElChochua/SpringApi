package com.example.springApi.controller;

import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Dtos.Transactions.TransactionsDto;
import com.example.springApi.Repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/transactions")
public class TransactionsController {
    @Autowired
    private TransactionsRepository transactionsRepository;
    @GetMapping("/get-all-transactions")
    public ResponseEntity<?> getAllTransactions (){
        List<TransactionsDto> transactions = transactionsRepository.getAllTransactions();
        if(transactions.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseDto("No transactions available", 404));
        }
        return ResponseEntity.ok(transactions);
    }
    @GetMapping("/get-all-transactions-by-organization/{organization_id}")
    public ResponseEntity<?> getAllTransactionsByOrganization(@PathVariable("organization_id") int organization_id){
        List<TransactionsDto> transactions = transactionsRepository.getAllTransactionsByOrganization(organization_id);
        if(transactions.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseDto("No transactions available", 404));
        }
        return  ResponseEntity.ok(transactions);
    }
    @GetMapping("/get-all-transactions-by-user/{user_id}")
    public ResponseEntity<?> getAllTransactionsByUser(@PathVariable("user_id") int user_id){
        List<TransactionsDto> transactions = transactionsRepository.getAllTransactionsByUser(user_id);
        if(transactions.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseDto("No transactions available", 404));
        }
        return ResponseEntity.ok(transactions);
    }
}
