package com.example.springApi.Repositories;

import com.example.springApi.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserInterface extends CrudRepository<User, Integer> {

}
