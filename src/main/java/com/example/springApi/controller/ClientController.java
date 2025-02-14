package com.example.springApi.controller;

import com.example.springApi.Repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/client")
public class ClientController {
    @Autowired
    OrganizationRepository organizationRepository;

}
