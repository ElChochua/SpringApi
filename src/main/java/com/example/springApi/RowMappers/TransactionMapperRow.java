package com.example.springApi.RowMappers;

import com.example.springApi.Dtos.Transactions.TransactionDto;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapperRow implements RowMapper<TransactionDto> {
   @Override
    public TransactionDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransaction_ID(rs.getInt("transaction_ID"));
        transactionDto.setLoan_ID(rs.getInt("loan_ID"));
        transactionDto.setUser_ID(rs.getInt("user_ID"));
        transactionDto.setTransaction_type(rs.getString("transaction_type"));
        transactionDto.setAmount(rs.getDouble("amount"));
        transactionDto.setIssued_at(rs.getString("issued_at"));
        transactionDto.setDescription(rs.getString("description"));
        return transactionDto;
}
}
