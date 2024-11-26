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
    private static final String SECRET = "TExBVkVfTVVZX1NFQ1JFVEzE3Zmxu7BSGSJx72BSBXM";
    private static final long EXPIRATION = 1000*60*30;

    public String generateToken(UserDetails userDetails, String role){
        var claims = new HashMap<String, Object>();
        claims.put("role",role);
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }

    public String generateRefreshToken(UserDetails userDetails, String role) {
        var claims = new HashMap<String, Object>();
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        return extractClaims(token,Claims::getSubject).equals(userDetails.getUsername())
                && !extractClaims(token, Claims::getExpiration).before(new Date());
    }
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        Claims claims = Jwts.parser().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();

        return claimsResolver.apply(claims);
    }
    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }
}
