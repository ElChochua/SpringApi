package com.example.springApi.controller;

import com.example.springApi.Repositories.OrganizationRepository;
import com.example.springApi.dto.authDto.ResponseDto;
import com.example.springApi.dto.authDto.organizationsDtos.OrganizationDetailsDto;
import com.example.springApi.dto.authDto.organizationsDtos.OrganizationRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/v1/organization")
public class OrganizationController {
    @Autowired
    OrganizationRepository organizationRepository;
    @PostMapping("/register-organization")
    public ResponseEntity<?> registerOrganization(@RequestBody OrganizationRegisterDto organization){
        ResponseDto response = organizationRepository.registerOrganization(organization);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-all-organizations")
    public ResponseEntity<?> getAllOrganizations(){
        List<OrganizationDetailsDto> organizations = organizationRepository.getAllOrganizations();
        if(organizations.isEmpty()){
            return ResponseEntity.badRequest().body("No organizations available");
        }
        return ResponseEntity.ok(organizations);
    }
    @GetMapping("/get-all-organizations-inactives")
    public ResponseEntity<?> getAllOrganizationsInactives(){
        List<OrganizationDetailsDto> organizations = organizationRepository.getAllOrganizationInactives();
        if(organizations.isEmpty()){
            return ResponseEntity.badRequest().body("No organizations available");
        }
        return ResponseEntity.ok(organizations);
    }
    @GetMapping("/get-all-organizations-actives")
    public ResponseEntity<?> getAllOrganizationsActives(){
        List<OrganizationDetailsDto> organizations = organizationRepository.getAllOrganizationActives();
        if(organizations.isEmpty()){
            return ResponseEntity.badRequest().body("No organizations available");
        }
        return ResponseEntity.ok(organizations);
    }
    @GetMapping("/get-all-organizations-by-owner/{owner_id}")
    public ResponseEntity<?> getAllOrganizationByOwner(@PathVariable int owner_id){
        List<OrganizationDetailsDto> organizations = organizationRepository.getAllOrganizationByOwner(owner_id);
        if(organizations.isEmpty()){
            return ResponseEntity.badRequest().body("No organizations available");
        }
        return ResponseEntity.ok(organizations);
    }
    @PostMapping("/add-user-to-organization")
    public ResponseEntity<?> addUserToOrganization(@RequestBody int user_id, int organization_id){
        ResponseDto response = organizationRepository.addUserToOrganization(user_id, organization_id);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @PutMapping("/update-organization-status")
    public ResponseEntity<?> updateOrganizationStatus(@RequestBody int organization_id, String status){
        ResponseDto response = organizationRepository.updateOrganizationStatus(organization_id, status);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @PutMapping("/update-organization-details")
    public ResponseEntity<?> updateOrganizationDetails(@RequestBody OrganizationRegisterDto organization){
        ResponseDto response = organizationRepository.updateOrganizationDetails(organization);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
