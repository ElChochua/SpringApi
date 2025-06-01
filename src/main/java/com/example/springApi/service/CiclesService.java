package com.example.springApi.service;

import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Model.CiclesModel;
import com.example.springApi.Repositories.CiclesRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiclesService {
    @Autowired
    private CiclesRepository ciclesRepository;
    public ResponseDto save(CiclesModel cicle) {
        try {
            ciclesRepository.save(cicle);
            return new ResponseDto("Cicle saved successfully", 200);
        } catch (Exception e) {
            return new ResponseDto("Failed to save cicle: " + e.getMessage(), 500);
        }
    }
}
