package com.walletapp.service;

import com.walletapp.config.JwtTokenProvider;
import com.walletapp.dto.*;
import com.walletapp.model.Customer;
import com.walletapp.repository.CustomerRepository;
import com.walletapp.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TransactionService transactionService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public WalletResponse createAccount(CustomerRequest customerRequest) {
        /**
         * Creating an account - saving a new user into the db
         * check if user already has an account
         */
        if (customerRepository.existsByEmail(customerRequest.getEmail())){
            return WalletResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        Customer newCustomer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .otherName(customerRequest.getOtherName())
                .gender(customerRequest.getGender())
                .address(customerRequest.getAddress())
                .stateOfOrigin(customerRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(customerRequest.getEmail())
                .password(passwordEncoder.encode(customerRequest.getPassword()))
                .phoneNumber(customerRequest.getPhoneNumber())
                .alternativePhoneNumber(customerRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();

        Customer savedCustomer = customerRepository.save(newCustomer);
        return WalletResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedCustomer.getAccountBalance())
                        .accountNumber(savedCustomer.getAccountNumber())
                        .accountName(savedCustomer.getFirstName() + " " + savedCustomer.getLastName() + " " + savedCustomer.getOtherName())
                        .build())
                .build();

    }
    public WalletResponse login(LoginDto loginDto){
        Authentication authentication = null;
        authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        return WalletResponse.builder()
                .responseCode("Login Successful")
                .responseMessage(jwtTokenProvider.generateToken(authentication))
                .build();
    }

    @Override
    public WalletResponse balanceEnquiry(EnquiryRequest request) {
        //check if the provided account number exists in the db
        boolean isAccountExist = customerRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist){
            return WalletResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        Customer foundCustomer = customerRepository.findByAccountNumber(request.getAccountNumber());
        return WalletResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundCustomer.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .accountName(foundCustomer.getFirstName() + " " + foundCustomer.getLastName() + " " + foundCustomer.getOtherName())
                        .build())
                .build();
    }


    @Override
    public WalletResponse creditAccount(CreditDebitRequest request) {
        //checking if the account exists
        boolean isAccountExist = customerRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist){
            return WalletResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        Customer customerToCredit = customerRepository.findByAccountNumber(request.getAccountNumber());
        customerToCredit.setAccountBalance(customerToCredit.getAccountBalance().add(request.getAmount()));
        customerRepository.save(customerToCredit);
        //save the transaction
        TransactionDto transactionDto = TransactionDto.builder()
                .accountNumber(customerToCredit.getAccountNumber())
                .transactionType("CREDIT")
                .amount(request.getAmount())
                .build();
        transactionService.saveTransaction(transactionDto);
        return WalletResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(customerToCredit.getFirstName() + " " + customerToCredit.getLastName() + " " + customerToCredit.getOtherName())
                        .accountBalance(customerToCredit.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public WalletResponse debitAccount(CreditDebitRequest request) {
        //check if the account exists
        //check if the amount you intend to withdraw is not more than the current account balance
        boolean isAccountExist = customerRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist){
            return WalletResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        Customer customerToDebit = customerRepository.findByAccountNumber(request.getAccountNumber());
        BigInteger availableBalance =customerToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = request.getAmount().toBigInteger();
        if ( availableBalance.intValue() < debitAmount.intValue()){
            return WalletResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        else {
            customerToDebit.setAccountBalance(customerToDebit.getAccountBalance().subtract(request.getAmount()));
            customerRepository.save(customerToDebit);
            TransactionDto transactionDto = TransactionDto.builder()
                    .accountNumber(customerToDebit.getAccountNumber())
                    .transactionType("DEBIT")
                    .amount(request.getAmount())
                    .build();
            transactionService.saveTransaction(transactionDto);
            return WalletResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(request.getAccountNumber())
                            .accountName(customerToDebit.getFirstName() + " " + customerToDebit.getLastName() + " " + customerToDebit.getOtherName())
                            .accountBalance(customerToDebit.getAccountBalance())
                            .build())
                    .build();
        }

    }
}
