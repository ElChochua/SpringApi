package com.example.springApi.Filter;

import com.example.springApi.service.JwtUtilService;
import com.example.springApi.service.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private final JwtUtilService jwtUtilService;
    @Autowired
    private UserDetailServiceImpl userDetailsService;
    public JwtRequestFilter(JwtUtilService jwtUtilService) {
        this.jwtUtilService = jwtUtilService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String AuthorizationHeader = request.getHeader("Authorization");
        if(AuthorizationHeader != null && AuthorizationHeader.startsWith("Bearer ")){
            String jwt = AuthorizationHeader.substring(7);
            String email = jwtUtilService.extractUsername(jwt);
            if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = null;
                try {
                    userDetails = this.userDetailsService.loadUserByEmail(email);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if(jwtUtilService.validateToken(jwt, userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            (userDetails), null, userDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
