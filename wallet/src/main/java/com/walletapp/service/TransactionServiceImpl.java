package com.walletapp.service;

import com.walletapp.dto.TransactionDto;
import com.walletapp.model.Transaction;
import com.walletapp.repository.TransactionRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public void saveTransaction(TransactionDto transactionDto) {
     Transaction transaction =    Transaction.builder()
                 .transactionType(transactionDto.getTransactionType())
                 .accountNumber(transactionDto.getAccountNumber())
                 .amount(transactionDto.getAmount())
                 .status("SUCCESS")
                 .build();
         transactionRepository.save(transaction);
         System.out.println("Transaction saved successfully");
    }
}
