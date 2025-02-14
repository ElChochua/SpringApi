package com.example.springApi.RowMappers;

import com.example.springApi.Dtos.Transactions.TransactionsDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionsMapperRow implements RowMapper<TransactionsDto> {
    @Override
    public TransactionsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionsDto transactionsDto = new TransactionsDto();
        transactionsDto.setTransaction_ID(rs.getInt("transaction_ID"));
        transactionsDto.setLoan_ID(rs.getInt("loan_ID"));
        transactionsDto.setUser_ID(rs.getInt("user_ID"));
        transactionsDto.setTransaction_type(rs.getString("transaction_type"));
        transactionsDto.setAmount(rs.getDouble("amount"));
        transactionsDto.setIssued_at(rs.getString("issued_at"));
        transactionsDto.setTransaction_description(rs.getString("description"));
        return transactionsDto;
    }
}
