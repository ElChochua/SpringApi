package com.example.springApi.Repositories;

import com.example.springApi.Dtos.ResponseDto;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoanInterface {
    ResponseDto registerLoan();
}
