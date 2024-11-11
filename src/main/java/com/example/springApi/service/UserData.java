package com.example.springApi.service;
import com.example.springApi.Interfaces.UserDetailsImpl;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class UserData extends User implements UserDetailsImpl {
    @Getter
    private String email;

    public UserData(String email, String password, Collection<? extends GrantedAuthority> roles) {
        super(email, password, true, true,
true, true, roles);
        this.email = email;
    }
}
