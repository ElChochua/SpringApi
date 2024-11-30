package com.example.springApi.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class User {
    private String email;
    private String password;
}
