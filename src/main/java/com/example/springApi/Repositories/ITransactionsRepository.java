package com.example.springApi.Repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionsRepository{
    void registerTransaction();
}
