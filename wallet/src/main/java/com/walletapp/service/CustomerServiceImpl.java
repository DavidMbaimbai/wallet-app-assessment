package com.walletapp.service;

import com.walletapp.dto.*;
import com.walletapp.model.Customer;
import com.walletapp.repository.CustomerRepository;
import com.walletapp.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmailService emailService;

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
                .phoneNumber(customerRequest.getPhoneNumber())
                .alternativePhoneNumber(customerRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();

        Customer savedCustomer = customerRepository.save(newCustomer);
        //Send email Alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedCustomer.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("Congratulations! Your Account Has been Successfully Created.\nYour Account Details: \n" +
                        "Account Name: " + savedCustomer.getFirstName() + " " + savedCustomer.getLastName() + " " + savedCustomer.getOtherName() + "\nAccount Number: " + savedCustomer.getAccountNumber())
                .build();
        emailService.sendEmailAlert(emailDetails);

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
    public String nameEnquiry(EnquiryRequest request) {
        boolean isAccountExist = customerRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist){
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }
        Customer foundCustomer = customerRepository.findByAccountNumber(request.getAccountNumber());
        return foundCustomer.getFirstName() + " " + foundCustomer.getLastName() + " " + foundCustomer.getOtherName();
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

    @Override
    public WalletResponse transfer(TransferRequest transferRequest) {
        boolean isDestinationAccountExist = customerRepository.existsByAccountNumber(transferRequest.getDestinationAccountNumber());
        if(!isDestinationAccountExist){
            return WalletResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        Customer sourceAccountCustomer =
                customerRepository.findByAccountNumber(transferRequest.getSourceAccountNumber());
        if (transferRequest.getAmount().compareTo(sourceAccountCustomer.getAccountBalance()) > 0){
            return WalletResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        sourceAccountCustomer.setAccountBalance(sourceAccountCustomer
                .getAccountBalance().subtract(transferRequest.getAmount()));
        String sourceUsername  = sourceAccountCustomer.getFirstName() + " " +
                sourceAccountCustomer.getLastName() + " " + sourceAccountCustomer.getOtherName();

        customerRepository.save(sourceAccountCustomer);
        EmailDetails debitAlert  = EmailDetails.builder()
                .subject("DEBIT ALERT")
                .recipient(sourceAccountCustomer.getEmail())
                .messageBody("The sum of " + transferRequest.getAmount() + "has been deducted from your account! Your balance is " + sourceAccountCustomer.getAccountBalance())
                .build();
        emailService.sendEmailAlert(debitAlert);
        Customer destiantionAccountCustomer = customerRepository.findByAccountNumber(transferRequest.getDestinationAccountNumber());
        destiantionAccountCustomer.setAccountBalance(destiantionAccountCustomer.getAccountBalance().add(transferRequest.getAmount()));
        //String recepientUsername  = destiantionAccountCustomer.getFirstName() + " " +destiantionAccountCustomer.getLastName() + " " + destiantionAccountCustomer.getOtherName();
        customerRepository.save(destiantionAccountCustomer);
        EmailDetails creditAlert  = EmailDetails.builder()
                .subject("CREDIT ALERT")
                .recipient(sourceAccountCustomer.getEmail())
                .messageBody("The sum of " + transferRequest.getAmount() + "has been sent to your account from " +sourceUsername +  "Your current balance is " + sourceAccountCustomer.getAccountBalance())
                .build();
        emailService.sendEmailAlert(creditAlert);

        return WalletResponse.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .accountInfo(null)
                .build();
    }
}
