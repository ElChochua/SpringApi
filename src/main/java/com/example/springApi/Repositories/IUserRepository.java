package com.example.springApi.Repositories;

import com.example.springApi.Model.UserModel;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository {
    public UserModel findByUser(String user);
    public UserModel findAll();
    public UserModel findByEmail(String email);

}
