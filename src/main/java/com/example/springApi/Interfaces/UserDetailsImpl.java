package com.example.springApi.Interfaces;


import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsImpl extends UserDetails {
    String getEmail();
}
