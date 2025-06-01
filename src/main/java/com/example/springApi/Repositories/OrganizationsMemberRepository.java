package com.example.springApi.Repositories;

import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Dtos.UsersDtos.UserDetailsDto;
import com.example.springApi.Model.OrganizationsMember;
import com.example.springApi.Model.OrganizationsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationsMemberRepository extends JpaRepository <OrganizationsMember, Integer>{
    ResponseDto saveOrganizationMember(OrganizationsMember organizationMember);
    @Query("SELECT om.organization FROM OrganizationsMember om WHERE om.user.User_ID = ?1")
    List<OrganizationsModel> findOrganizationsByMemberId(int member_id);
    ResponseDto removeByOrganization_member_id(int member_id);
}   List<UserDetailsDto>
