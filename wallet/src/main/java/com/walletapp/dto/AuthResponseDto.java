package com.walletapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthResponseDto {
    @Schema(
            name = "Customer token"
    )
    private String token;
    @Schema(
            name = "type"
    )
    private String type;

    public AuthResponseDto(String token){
        this.token = token;
    }
}
