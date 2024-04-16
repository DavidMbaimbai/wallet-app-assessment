package com.walletapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @Schema(
            name = "Customer email"
    )
    private String email;
    @Schema(
            name = "Customer password"
    )
    private String password;
}
