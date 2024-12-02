package com.example.springApi.controller;

import com.example.springApi.Model.UserModel;
import com.example.springApi.Repositories.UserRepository;
import com.example.springApi.dto.authDto.AuthRequestDto;
import com.example.springApi.dto.authDto.AuthResponseDto;
import com.example.springApi.dto.authDto.RegisterDto;
import com.example.springApi.dto.authDto.UsersDtos.UsersDto;
import com.example.springApi.service.JwtUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/auth")
public class AuthController {
    private static final Logger loger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtilService jwtUtilService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequestDto authRequestDto){
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
            //Validar User en Bd
            UserDetails userDetails =  this.userDetailsService.loadUserByUsername(authRequestDto.getEmail());
            //Generar Token
            UserModel user = userRepository.findByEmail(authRequestDto.getEmail());
            String jwt = this.jwtUtilService.generateToken(userDetails, user.getRole());
            String refreshToken = this.jwtUtilService.generateRefreshToken(userDetails, user.getRole());
            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setToken(jwt);
            authResponseDto.setTokenRefresh(refreshToken);
            return new ResponseEntity<>(authResponseDto, org.springframework.http.HttpStatus.OK);
        }catch(Exception e){
            if(e.toString().contains("Incorrect result size: expected 1, actual 0")){
                return new ResponseEntity<>( " Usuario no encontrado", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(e + " Error al autenticar", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody AuthRequestDto authRequestDto){
        try{
            UserDetails userDetails =  this.userDetailsService.loadUserByUsername(authRequestDto.getEmail());
            UserModel user = userRepository.findByEmail(authRequestDto.getEmail());
            String jwt = this.jwtUtilService.generateToken(userDetails, user.getRole());
            String refreshToken = this.jwtUtilService.generateRefreshToken(userDetails, user.getRole());
            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setToken(jwt);
            authResponseDto.setTokenRefresh(refreshToken);
            return new ResponseEntity<>(authResponseDto, org.springframework.http.HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e + " Error al refrescar", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto userRegister) {
        try {
            userRepository.selfRegisterUser(userRegister);
        } catch (Exception e) {
            if (e.toString().contains("Duplicate entry")) {
                return ResponseEntity.badRequest().body("El usuario ya existe");
            }
            return ResponseEntity.badRequest().body(e.toString() + "No se pudo registrar el usuario");
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/unassigned-users")
    public ResponseEntity<?> getUnassignedUsers(){
        List<UsersDto> users = userRepository.getAllUnassignedUsers();
        return ResponseEntity.ok(users);
    }
}
