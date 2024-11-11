package com.example.springApi.service;

import com.example.springApi.Model.UserModel;
import com.example.springApi.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = this.userRepository.findByUser(username);

        if(user == null){
            throw new UsernameNotFoundException(username);
        }

        return new User(user.getUser(), user.getPassword(), new ArrayList<>());
    }
    public UserDetails loadUserByEmail(String email) throws Exception{
        UserModel user = this.userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("User not found");
        }
        return new UserData(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
