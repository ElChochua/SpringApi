package com.example.springApi.controller;

import com.example.springApi.Model.User;
import com.example.springApi.Repositories.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class Controller{
    @Autowired
    private UserInterface userInterface;
    @PostMapping("/add")
    public @ResponseBody String addUser(@RequestParam String name, @RequestParam int age){
        User n = new User();
        n.setName(name);
        n.setAge(age);
        userInterface.save(n);
        return "Saved";
    }
    @GetMapping
    public String index(Principal principal){
        return "ola " + principal.getName();
    }
    @GetMapping("/all")
    public @ResponseBody List<User> getAllUsers(){
        return (List<User>) userInterface.findAll();
    }
}
