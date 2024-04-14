package com.walletapp.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.walletapp.model.Transaction;
import com.walletapp.service.TransactionHistory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class TransactionHistoryController {
    private TransactionHistory transactionHistory;


    @GetMapping("/history")
    public List<Transaction> generateTransactionHistory(@RequestParam String accountNumber,
                                                        @RequestParam String startDate,
                                                        @RequestParam String endDate){
        return transactionHistory.generateTransactionHistory(accountNumber, startDate, endDate);
    }
}
