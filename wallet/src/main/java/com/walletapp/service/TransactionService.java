package com.walletapp.service;

import com.walletapp.dto.TransactionDto;
import com.walletapp.model.Transaction;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);
}
