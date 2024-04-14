package com.walletapp.controller;

import com.walletapp.dto.*;
import com.walletapp.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@Tag(name= "Customer Managemenet APIs")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Operation(
            summary = "Create new Customer REST API",
            description = "REST API to create new Customer inside a Wallet App"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            )
    })
    @PostMapping("/create")
    public WalletResponse createAccount(@RequestBody CustomerRequest customerRequest){
        return customerService.createAccount(customerRequest);
    }
    @Operation(
            summary = "Login REST API",
            description = "REST API to login inside a Wallet App"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status SUCCESSFUL"
            )
    })
    @PostMapping("/login")
    public WalletResponse login(@RequestBody LoginDto loginDto){
        return customerService.login(loginDto);
    }
    @Operation(
            summary = "Balance Enquiry  REST API",
            description = "Given account number, check how much the customer has"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status SUCCESS"
            )
    })
    @GetMapping("/balance-enquiry")
    public WalletResponse balanceEnquiry(@RequestBody EnquiryRequest request){
        return customerService.balanceEnquiry(request);
    }
    @Operation(
            summary = "Customer Account Credit REST API",
            description = "REST API for a credit"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status SUCCESSFUL"
            )
    })
    @PostMapping("/credit")
    public WalletResponse creditAccount(@RequestBody CreditDebitRequest request){
        return customerService.creditAccount(request);
    }
    @Operation(
            summary = "Customer Account Debit REST API",
            description = "REST API for a debit"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status SUCCESSFUL"
            )
    })
    @PostMapping("/debit")
    public WalletResponse debitAccount(@RequestBody CreditDebitRequest request){
        return customerService.debitAccount(request);
    }


}
