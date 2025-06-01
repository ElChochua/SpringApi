package com.example.springApi.service;

import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Model.OrganizationsModel;
import com.example.springApi.Repositories.IOrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationsService {
    @Autowired
    private IOrganizationRepository organizationRepository;

    public ResponseDto saveOrganization(OrganizationsModel organization) {
        try {
            organizationRepository.saveOrganization(organization);
            return new ResponseDto("Organization saved successfully", 200);
        } catch (Exception e) {
            return new ResponseDto("Failed to save organization: " + e.getMessage(), 500);
        }
    }
    public ResponseDto save(OrganizationsModel organization) {
        try {
            organizationRepository.save(organization);
            return new ResponseDto("Organization saved successfully", 200);
        } catch (Exception e) {
            return new ResponseDto("Failed to save organization: " + e.getMessage(), 500);
        }
    }
    public List<OrganizationsModel> getAllOrganizations() {
        try {
            return organizationRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve organizations: " + e.getMessage());
        }
    }
    public List<OrganizationsModel> getAllInactiveOrganizaitons() {
        try {
            return organizationRepository.findByStatus("INACTIVE");
        }catch (Exception e){
            throw new RuntimeException("Failed to retrieve inactive organizations: " + e.getMessage());
        }
    }
    public List<OrganizationsModel> getAllActiveOrganizaitons() {
        try {
            return organizationRepository.findByStatus("ACTIVE");
        }catch (Exception e){
            throw new RuntimeException("Failed to retrieve active organizations: " + e.getMessage());
        }
    }
    public List<OrganizationsModel> getAllOrganizationsByOwner(int owner_id) {
        try {
            return organizationRepository.findByOwner_Id(owner_id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve organizations by owner: " + e.getMessage());
        }
    }
    public ResponseDto removeById(int id) {
        try {
            return organizationRepository.removeById(id);
        } catch (Exception e) {
            return new ResponseDto("Failed to remove organization: " + e.getMessage(), 500);
        }
    }
    public ResponseDto removeAllByOwner(int owner_id) {
        try {
            return organizationRepository.removeAllByOwner_Id(owner_id);
        } catch (Exception e) {
            return new ResponseDto("Failed to remove organizations by owner: " + e.getMessage(), 500);
        }
    }
}
