package com.example.springApi.service;

import com.example.springApi.Model.UserModel;
import com.example.springApi.Repositories.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private static final Logger loger = LoggerFactory.getLogger(UserDetailServiceImpl.class);
    @Autowired
    private IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = this.userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("Email no encontrado");
        }
        Set<GrantedAuthority> grantedAuthorities = getAuthority(user.getRole());
        loger.info("User role {}", user.getRole());
        loger.info("Granted Authorities {}", grantedAuthorities);
        return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
    private Set<GrantedAuthority> getAuthority(String role) {
        Set<String> roles = Set.of(role);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
