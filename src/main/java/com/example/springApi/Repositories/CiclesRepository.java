package com.example.springApi.Repositories;

import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Model.CiclesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiclesRepository  extends JpaRepository<CiclesModel, Integer>{
    ResponseDto saveCicle(CiclesModel cicle);
}
