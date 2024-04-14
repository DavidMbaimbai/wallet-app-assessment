package com.walletapp.repository;

import com.walletapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String username);
    Boolean existsByEmail(String email);
    Boolean existsByAccountNumber(String accountNumber);
    Customer findByAccountNumber(String accountNumber);

}
