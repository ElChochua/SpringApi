package com.example.springApi.controller;

import com.example.springApi.Dtos.UsersDtos.UserDetailDto;
import com.example.springApi.Dtos.organizationsDtos.*;
import com.example.springApi.Repositories.OrganizationRepository;
import com.example.springApi.Dtos.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("api/v1/organization")
public class OrganizationController {
    @Autowired
    OrganizationRepository organizationRepository;
    Logger logger = Logger.getLogger(OrganizationController.class.getName());
    @PostMapping("/register-organization")
    public ResponseEntity<?> registerOrganization(@RequestBody OrganizationRegisterDto organization){
        ResponseDto response = organizationRepository.registerOrganization(organization);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-all-organizations-details")
    public ResponseEntity<?> getAllOrganizationsDetails(){
        List<OrganizationDetailsDto> organizations = organizationRepository.getAllOrganizationsDetails();
        if(organizations.isEmpty()){
            return ResponseEntity.badRequest().body("No organizations available");
        }
        return ResponseEntity.ok(organizations);
    }
    @GetMapping("/get-all-organizations")
    public ResponseEntity<?> getAllOrganizations(){
        List<OrganizationDto> organizations = organizationRepository.getAllOrganizations();
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
    public ResponseEntity<?> addUserToOrganization(@RequestBody AddOrganizationMemberDto addOrganizationMemberDto){
        ResponseDto response = organizationRepository.addUserToOrganization(addOrganizationMemberDto);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    //Endpoint to get all organization where a user is member
    @GetMapping("/get-all-organizations-by-user/{user_id}")
    public ResponseEntity<?> getAllOrganizationsByUser(@PathVariable int user_id){
        List<OrganizationDetailsDto> organizations = organizationRepository.getAllOrganizationsByUser(user_id);
        if(organizations.isEmpty()){
            return ResponseEntity.badRequest().body("No organizations available");
        }
        return ResponseEntity.ok(organizations);
    }
    @PutMapping("/update-organization-status")
    public ResponseEntity<?> updateOrganizationStatus(@RequestBody Map<String, Object> payload){
        int organization_id = (int) payload.get("organization_id");
        String status = (String) payload.get("status");
        System.out.println(organization_id + " " + status);
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
    @DeleteMapping("/delete-organization/{organization_id}")
    public ResponseEntity<?> deleteOrganization(@PathVariable int organization_id){
        System.out.println(organization_id);
        ResponseDto response = organizationRepository.deleteOrganization(organization_id);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete-user-from-organization/{user_id}/{organization_id}")
    public ResponseEntity<?> deleteUserFromOrganization(@PathVariable("user_id") int user_id, @PathVariable("organization_id") int organization_id){
        ResponseDto response = organizationRepository.deleteUserFromOrganization(organization_id, user_id);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-users-outside-organization/{organization_id}")
    public ResponseEntity<?> getUsersOutsideOrganization(@PathVariable int organization_id){
        List<OrganizationMemberDto> users = organizationRepository.getAllUsersOutOfOrganization(organization_id);
        if(users.isEmpty()){
            return ResponseEntity.badRequest().body("No users available");
        }
        return ResponseEntity.ok(users);
    }
    @PostMapping("/update-member-role")
    public ResponseEntity<?> updateMemberRole(@RequestBody OrganizationMemberDto organizationMemberDto){
        ResponseDto response = organizationRepository.updateMemberRole(organizationMemberDto);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/delete-member-from-organization/{organization_id}/{user_id}")
    public ResponseEntity<?> deleteMember(@PathVariable int organization_id, @PathVariable int user_id){
        ResponseDto response = organizationRepository.removeMemberFromOrganization(organization_id, user_id);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-organization-members/{organization_id}")
    public ResponseEntity<?> getOrganizationMembers(@PathVariable int organization_id){
        List<OrganizationMemberDto> users = organizationRepository.getAllOrganizationMembers(organization_id);
        if(users.isEmpty()){
            return ResponseEntity.badRequest().body("No users available");
        }
        return ResponseEntity.ok(users);
    }
}
