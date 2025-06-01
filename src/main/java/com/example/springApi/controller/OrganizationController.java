package com.example.springApi.controller;

import com.example.springApi.Dtos.organizationsDtos.*;
import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Model.CiclesModel;
import com.example.springApi.Model.OrganizationsMember;
import com.example.springApi.Model.OrganizationsModel;
import com.example.springApi.service.CiclesService;
import com.example.springApi.service.OrganizationsMemberService;
import com.example.springApi.service.OrganizationsService;
import org.apache.coyote.Response;
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
    OrganizationsService organizationsService;
    @Autowired
    CiclesService ciclesService;
    @Autowired
    OrganizationsMemberService organizationsMemberService;
    Logger logger = Logger.getLogger(OrganizationController.class.getName());
    @PostMapping("/register-organization")
    public ResponseEntity<?> registerOrganization(@RequestBody OrganizationsModel  organization, @RequestBody CiclesModel cicle){
        organizationsService.save(organization);
        cicle.setOrganization(organization);
        ciclesService.save(cicle);
        organization.setActual_cicle(cicle);
        ResponseDto response = organizationsService.save(organization);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok("Organization registered successfully");
    }
    @GetMapping("/get-all-organizations")
    public ResponseEntity<?> getAllOrganizations(){
        List<OrganizationsModel> organizations = organizationsService.getAllOrganizations();
        if(organizations.isEmpty()){
            return ResponseEntity.badRequest().body("No organizations available");
        }
        return ResponseEntity.ok(organizations);
    }
    @GetMapping("/get-all-organizations-inactives")
    public ResponseEntity<?> getAllOrganizationsInactives(){
        List<OrganizationsModel> organizations = organizationsService.getAllInactiveOrganizaitons();
        if(organizations.isEmpty()){
            return ResponseEntity.badRequest().body("No organizations available");
        }
        return ResponseEntity.ok(organizations);
    }
    @GetMapping("/get-all-organizations-actives")
    public ResponseEntity<?> getAllOrganizationsActives(){
        List<OrganizationsModel> organizations = organizationsService.getAllActiveOrganizaitons();
        if(organizations.isEmpty()){
            return ResponseEntity.badRequest().body("No organizations available");
        }
        return ResponseEntity.ok(organizations);
    }
    @GetMapping("/get-all-organizations-by-owner/{owner_id}")
    public ResponseEntity<?> getAllOrganizationByOwner(@PathVariable int owner_id){
        List<OrganizationsModel> organizations = organizationsService.getAllOrganizationsByOwner(owner_id);
        if(organizations.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseDto("No organizations available", 404));
        }
        return ResponseEntity.ok(organizations);
    }
    @PostMapping("/add-user-to-organization")
    public ResponseEntity<?> addUserToOrganization(@RequestBody OrganizationsModel organization, @RequestBody OrganizationsMember member){
        member.setOrganization(organization);
        ResponseDto response = organizationsService.saveOrganization(organization);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    //Endpoint to get all organization where a user is member
    @GetMapping("/get-all-organizations-by-user/{user_id}")
    public ResponseEntity<?> getAllOrganizationsByUser(@PathVariable int user_id){
        List<OrganizationsModel> organizations = organizationsMemberService.findOrganizationsByMemberId(user_id);
        if(organizations.isEmpty()){
            return ResponseEntity.badRequest().body("No organizations available");
        }
        return ResponseEntity.ok(organizations);
    }
    @PutMapping("/update-organization")
    public ResponseEntity<?> updateOrganizationStatus(@RequestBody OrganizationsModel organization){
        ResponseDto response = organizationsService.saveOrganization(organization);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete-organization/{organization_id}")
    public ResponseEntity<?> deleteOrganization(@PathVariable int organization_id){
        ResponseDto response =  organizationsService.removeById(organization_id);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete-user-from-organization/{member_id}")
    public ResponseEntity<?> deleteUserFromOrganization(@PathVariable("member_id") int member_id){
        ResponseDto response = organizationsMemberService.removeByOrganization_member_id(member_id);
        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-users-outside-organization/{organization_id}")
    public ResponseEntity<?> getUsersOutsideOrganization(@PathVariable int organization_id){

        if(users.isEmpty()){
            return ResponseEntity.badRequest().body("No users available");
        }
        return ResponseEntity.ok(users);
    }
    @PostMapping("/update-member-role")
    public ResponseEntity<?> updateMemberRole(@RequestBody OrganizationMemberDto organizationMemberDto){

        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/delete-member-from-organization/{organization_id}/{user_id}")
    public ResponseEntity<?> deleteMember(@PathVariable int organization_id, @PathVariable int user_id){

        if(response.getCode() != 200){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-organization-members/{organization_id}")
    public ResponseEntity<?> getOrganizationMembers(@PathVariable int organization_id){

        if(users.isEmpty()){
            return ResponseEntity.badRequest().body("No users available");
        }
        return ResponseEntity.ok(users);
    }
}
