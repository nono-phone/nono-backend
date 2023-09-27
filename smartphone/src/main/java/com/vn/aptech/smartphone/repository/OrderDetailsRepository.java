package com.vn.aptech.smartphone.repository;

import com.vn.aptech.smartphone.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
