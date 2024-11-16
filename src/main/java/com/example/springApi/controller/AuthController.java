package com.example.springApi.controller;

import com.example.springApi.dto.AuthRequestDto;
import com.example.springApi.service.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtilService jwtUtilService;
    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequestDto authRequestDto){
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));

            //Validar User en Bd
            UserDetails userDetails =  this.userDetailsService.loadUserByUsername(authRequestDto.getEmail());
            //Generar Token
            String jwt = this.jwtUtilService.generateToken(userDetails, "default");
            System.out.println(userDetails.getUsername());
            return new ResponseEntity<>(jwt, org.springframework.http.HttpStatus.OK);
        }catch(Exception e){
            UserDetails userDetails =  this.userDetailsService.loadUserByUsername(authRequestDto.getEmail());
            System.out.println(userDetails.getUsername());
            return new ResponseEntity<>("Invalid credentials", org.springframework.http.HttpStatus.FORBIDDEN);
        }
    }
}
