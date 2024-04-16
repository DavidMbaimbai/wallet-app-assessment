package com.walletapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionDto {
    @Schema(
            name = "Transaction type"
    )
    private String transactionType;
    @Schema(
            name = "amount"
    )
    private BigDecimal amount;
    @Schema(
            name = "Customer account number"
    )
    private String accountNumber;
    @Schema(
            name = "account status"
    )
    private String status;
}
