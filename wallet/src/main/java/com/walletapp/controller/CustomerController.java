package com.walletapp.controller;

import com.walletapp.dto.*;
import com.walletapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")

public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/create")
    public WalletResponse createAccount(@RequestBody CustomerRequest customerRequest){
        return customerService.createAccount(customerRequest);
    }

    @GetMapping("/balance-enquiry")
    public WalletResponse balanceEnquiry(@RequestBody EnquiryRequest request){
        return customerService.balanceEnquiry(request);
    }

    @GetMapping("/name-enquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest request){
        return customerService.nameEnquiry(request);
    }

    @PostMapping("/credit")
    public WalletResponse creditAccount(@RequestBody CreditDebitRequest request){
        return customerService.creditAccount(request);
    }

    @PostMapping("/debit")
    public WalletResponse debitAccount(@RequestBody CreditDebitRequest request){
        return customerService.debitAccount(request);
    }

    @PostMapping("/transfer")
    public WalletResponse transfer(@RequestBody TransferRequest transferRequest){
        return customerService.transfer(transferRequest);
    }

}
