package com.walletapp.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.walletapp.model.Transaction;
import com.walletapp.service.TransactionHistory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/transaction")
@AllArgsConstructor
@Tag(name= "Transaction history  API")
public class TransactionHistoryController {
    private TransactionHistory transactionHistory;

    @Operation(
            summary = "Transaction History  REST API",
            description = "check the transaction history of a customer"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status SUCCESS"
            )
    })
    @GetMapping("/history")
    public ResponseEntity<List<Transaction>> generateTransactionHistory(@RequestParam String accountNumber,
                                                                       @RequestParam String startDate,
                                                                       @RequestParam String endDate){
        List<Transaction> transactions = transactionHistory.generateTransactionHistory(accountNumber, startDate, endDate);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
