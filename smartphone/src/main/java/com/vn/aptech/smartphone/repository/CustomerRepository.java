package com.vn.aptech.smartphone.repository;

import com.vn.aptech.smartphone.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByEmail(String email);
}
