package com.example.springApi.service;

import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Model.OrganizationsMember;
import com.example.springApi.Model.OrganizationsModel;
import com.example.springApi.Repositories.OrganizationsMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationsMemberService {
    @Autowired
    private OrganizationsMemberRepository organizationsMemberRepository;
    public ResponseDto saveOrganizationMember(OrganizationsMember member){
        try {
            organizationsMemberRepository.save(member);
            return new ResponseDto("Organization member saved successfully", 200);
        } catch (Exception e) {
            return new ResponseDto("Failed to save organization member: " + e.getMessage(), 500);
        }
    }
    public List<OrganizationsModel> findOrganizationsByMemberId(int member_id){
        try{
            return organizationsMemberRepository.findOrganizationsByMemberId(member_id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve organizations by member ID: " + e.getMessage());
        }
    }
    public ResponseDto removeByOrganization_member_id(int member_id){
        try {
            organizationsMemberRepository.removeByOrganization_member_id(member_id);
            return new ResponseDto("Organization member removed successfully", 200);
        } catch (Exception e) {
            return new ResponseDto("Failed to remove organization member: " + e.getMessage(), 500);
        }
    }
}
