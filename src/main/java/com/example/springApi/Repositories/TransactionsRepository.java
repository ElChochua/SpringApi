package com.example.springApi.Repositories;

import com.example.springApi.Dtos.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    void registerRransaction() {}
    /*
    ResponseDto registerPayment() {
        try {
            String Query = "INSERT INTO organizations(organization_name, owner_ID, description) VALUES (?,?,?)";
            jdbcTemplate.update(Query, );
        } catch (Exception e) {
            if(e.getMessage().contains("Duplicate entry")){
                return new ResponseDto("Organization already exists",400);
            }else{
                System.out.println(e);
                return new ResponseDto("Organization could not be registered Error: " + e.toString(), 500);
            }
        }
        return new ResponseDto("Organization registered successfully",200);
    }

     */
}
