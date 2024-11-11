package com.example.springApi.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class UserModel {
    @Id
    Long User_ID;
    String User;
    String Email;
    String Password;
}
