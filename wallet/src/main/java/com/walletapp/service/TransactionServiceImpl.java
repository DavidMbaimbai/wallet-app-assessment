package com.walletapp.service;

import com.walletapp.dto.TransactionDto;
import com.walletapp.model.Transaction;
import com.walletapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public void saveTransaction(TransactionDto transactionDto) {
         Transaction.builder()
                 .transactionType(transactionDto.getTransactionType())
                 .accountNumber(transactionDto.getAccountNumber())
                 .amount(transactionDto.getAmount())
                 .status("SUCCESS")
                 .build();
    }
}
