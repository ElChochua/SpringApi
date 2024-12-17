package com.example.springApi.Dtos;

import lombok.Data;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Data
public class AuthRequestDto {
    @NotNull
    private String email;
    @NotNull
    private String password;


}
