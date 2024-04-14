package com.walletapp.service;

import com.walletapp.model.Transaction;
import com.walletapp.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
public class TransactionHistory {
    private TransactionRepository transactionRepository;

    public List<Transaction> generateTransactionHistory(String accountNumber, String startDate, String endDate){
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end   = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        List<Transaction> transactionList = transactionRepository.findAll()
                .stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getCreatedAt().isEqual(ChronoLocalDateTime.from(start)))
                        .filter(transaction -> transaction.getCreatedAt().isEqual(end.atStartOfDay())).toList();
        return transactionList;
    }
}
