package com.example.springApi.controller;

import com.example.springApi.dto.AuthRequestDto;
import com.example.springApi.service.JwtUtilService;
import com.example.springApi.service.UserData;
import com.example.springApi.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
    private UserDetailServiceImpl userDetailsService;
    @Autowired
    private JwtUtilService jwtUtilService;
    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequestDto authRequestDto) throws Exception {
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
            //Validar User en Bd
            UserData userDetails =  this.userDetailsService.loadUserByEmail(authRequestDto.getEmail());
            //Generar Token
            String jwt = this.jwtUtilService.generateToken(userDetails);
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch(Exception e){
            UserDetails userDetails =  this.userDetailsService.loadUserByEmail(authRequestDto.getEmail());
            return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDto authRequestDto){
        //Guardar en BD

        return new ResponseEntity<>("User registered", org.springframework.http.HttpStatus.OK);
    }
}
