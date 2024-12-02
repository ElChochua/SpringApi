package com.example.springApi.Model;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class UserModel {
    @Id
    Integer User_ID;
    String User;
    String Email;
    String Password;
    String Role;
}
