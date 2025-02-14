package com.example.springApi.Repositories;

import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Dtos.Transactions.TransactionsDto;
import com.example.springApi.RowMappers.TransactionMapperRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    void registerRransaction() {}
    public List<TransactionsDto> getAllTransactions(){
        String Query = "SELECT * FROM transactions";
        try {
         return jdbcTemplate. query(Query, new TransactionMapperRow());
        }catch (Exception e){
            return null;
        }
    }
    public List<TransactionsDto> getAllTransactionsByOrganization(int organization_id){
        String Query = "SELECT t.*\n" +
                "FROM transactions t\n" +
                "         JOIN loans l ON t.loan_id = l.loan_id\n" +
                "WHERE l.organization_id = ?;";
        try {
            return jdbcTemplate.query(Query, new TransactionMapperRow(), organization_id);
        }catch (Exception e){
            return null;
        }
    }
    public List<TransactionsDto> getAllTransactionsByUser(int user_id){
        String Query = "SELECT * FROM transactions WHERE user_id = ?";
        try {
            return jdbcTemplate.query(Query, new TransactionMapperRow(), user_id);
        }catch (Exception e){
            return null;
        }
    }
}
