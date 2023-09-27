package com.vn.aptech.smartphone.repository;

import com.vn.aptech.smartphone.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
