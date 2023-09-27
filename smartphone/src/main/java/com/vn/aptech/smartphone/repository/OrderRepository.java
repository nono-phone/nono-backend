package com.vn.aptech.smartphone.repository;

import com.vn.aptech.smartphone.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
