package com.example.springApi.Repositories;

import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Model.OrganizationsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrganizationRepository extends JpaRepository<OrganizationsModel, Integer> {
    ResponseDto saveOrganization(OrganizationsModel organization);
    List<OrganizationsModel> findByStatus(String s);
    List<OrganizationsModel> findByOwner_Id (int id);
    ResponseDto removeById(int id);
    ResponseDto removeAllByOwner_Id(int id);
}
