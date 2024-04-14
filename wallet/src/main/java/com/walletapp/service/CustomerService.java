package com.walletapp.service;

import com.walletapp.dto.*;

public interface CustomerService {
    WalletResponse createAccount(CustomerRequest customerRequest);
    WalletResponse balanceEnquiry(EnquiryRequest request);
    WalletResponse creditAccount(CreditDebitRequest request);
    WalletResponse debitAccount(CreditDebitRequest request);
    WalletResponse transfer(TransferRequest transferRequest);
    WalletResponse login(LoginDto loginDto);
}
