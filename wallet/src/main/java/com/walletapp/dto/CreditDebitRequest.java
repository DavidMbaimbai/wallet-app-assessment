package com.walletapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDebitRequest {
    @Schema(
            name = "Customer account number"
    )
    private String accountNumber;
    @Schema(
            name = "amount"
    )
    private BigDecimal amount;
}
