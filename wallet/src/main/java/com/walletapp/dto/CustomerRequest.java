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
public class CustomerRequest {
    @Schema(
            name = "Customer first name"
    )
    private String firstName;
    @Schema(
            name = "Customer last name"
    )
    private String lastName;
    @Schema(
            name = "Customer other name"
    )
    private String otherName;
    @Schema(
            name = "Customer gender"
    )
    private String gender;
    @Schema(
            name = "Customer address"
    )
    private String address;
    @Schema(
            name = "Customer state of origin"
    )
    private String stateOfOrigin;
    @Schema(
            name = "Customer email"
    )
    private String email;
    @Schema(
            name = "Customer password"
    )
    private String password;
    @Schema(
            name = "Customer phone number"
    )
    private String phoneNumber;
    @Schema(
            name = "Customer alternative phone number"
    )
    private String alternativePhoneNumber;
}
