package com.example.springApi.dto.authDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.coyote.Response;

@Getter
@Setter
@Data
public class ResponseDto {
    private String message;
    private int code;
    public ResponseDto(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
