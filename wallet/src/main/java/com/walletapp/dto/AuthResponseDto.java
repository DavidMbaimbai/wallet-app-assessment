package com.walletapp.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String token;
    private String type;

    public AuthResponseDto(String token){
        this.token = token;
    }
}
