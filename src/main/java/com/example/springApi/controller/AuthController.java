package com.example.springApi.controller;

import com.example.springApi.Dtos.*;
import com.example.springApi.Dtos.UsersDtos.UserDetailsDto;
import com.example.springApi.Model.UserModel;
import com.example.springApi.Repositories.UserRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

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
    @Autowired
    private PasswordEncoder passwordEncoder;
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
            LoginDto loginDto = new LoginDto();
            loginDto.setToken(jwt);
            loginDto.setRefreshToken(refreshToken);
            loginDto.setUser(this.userRepository.findByUserName(user.getUser()));

            return new ResponseEntity<>(loginDto, org.springframework.http.HttpStatus.OK);
        }catch(Exception e){
            if(e.toString().contains("Incorrect result size: expected 1, actual 0")){
                return new ResponseEntity<>( new ResponseDto("Usuario no encontrado", 404), HttpStatus.NOT_FOUND);
            }else if(e.toString().contains("Bad credentials")){
                return new ResponseEntity<>( new ResponseDto("Credenciales incorrectas", 401), HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(new ResponseDto("Error al autenticar", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request){
        String refreshToken = request.get("refreshToken");
        loger.info("Refresh Token: " + refreshToken);
        try{
           String email = this.jwtUtilService.extractEmail(refreshToken);
            UserDetails userDetails =  this.userDetailsService.loadUserByUsername(email);
            UserModel user = userRepository.findByEmail(email);
            if(jwtUtilService.validateToken(refreshToken, userDetails)) {
                String newJwt = jwtUtilService.generateToken(userDetails, user.getRole());
                String newRefreshToken = jwtUtilService.generateRefreshToken(userDetails, user.getRole());
                RefreshTokenDto refresResponse = new RefreshTokenDto();
                refresResponse.setToken(newJwt);
                refresResponse.setRefreshToken(newRefreshToken);
                return new ResponseEntity<>(refresResponse, org.springframework.http.HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Token invalido", HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception e){
            return new ResponseEntity<>(e + " Error al refrescar", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto userRegister, @RequestBody UserDetailsDto userDetailsDto) {
        try {
            userRepository.selfRegisterUser(userRegister);
            userRepository.updateUser(userDetailsDto);
        } catch (Exception e) {
            if (e.toString().contains("Duplicate entry")) {
                return ResponseEntity.badRequest().body("El usuario ya existe");
            }
            return ResponseEntity.badRequest().body(e.toString() + "No se pudo registrar el usuario");
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
