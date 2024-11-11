package com.example.springApi.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
@Service
public class JwtUtilService {
    private static final String SECRET = "fc4a092b49fad2089880bc19b103c93cff16c1875ffa9b9a8bcc295bbddd719e282f90597101b3b2b3ce3be366592f10ca7a25924db0e8d5bf23c08146f6d3660d89473b87ab3dbafbe12854427058e1f46e343f90481a0510cdee9feedf881066acc7c36ac84c10423b7818234492e1f57b7114be4d5eee114dca8fb1450b5a0061799e425b8a6c08f4377417d4b6fb2c140b469864c79c8733dccaf48f3b7066f681adfd2ed3d49ccac468808538bff4c685bbd9c1daaff57496c8e9319cb339841c68362d01d3296d2c72939245442dd740d01386496994ec1c42dbea9b111749c4f1f01ea3d1bd4495e5ff6bbef7c0197c54d22e332247234b5ec3a82499";
    private static final long EXPIRATION = 1000*60*30;

    public String generateToken(UserData userDetails){
        var claims = new HashMap<String, Object>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getEmail()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }
    public boolean validateToken(String token, UserDetails userDetails){
        return extractClaims(token,Claims::getSubject).equals(userDetails.getUsername())
                && !extractClaims(token, Claims::getExpiration).before(new Date());
    }
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        Claims claims = Jwts.parser().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();

        return claimsResolver.apply(claims);
    }
    public String extractEmail(String token){
        return extractClaims(token, Claims::getSubject);
    }
}
