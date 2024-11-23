package com.example.springApi.Repositories;

import com.example.springApi.Model.UserModel;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository {
    UserModel findByUser(String user);
    UserModel findByEmail(String email);
    void saveUser(UserModel user);
}
