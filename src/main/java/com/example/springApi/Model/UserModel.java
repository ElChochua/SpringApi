package com.example.springApi.Model;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserModel {
    @Id
    Integer User_ID;
    String User;
    String Email;
    String Password;
    String Token;
    String Role;
}
