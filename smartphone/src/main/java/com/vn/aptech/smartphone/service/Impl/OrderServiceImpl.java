package com.vn.aptech.smartphone.service.Impl;

import com.vn.aptech.smartphone.entity.Customer;
import com.vn.aptech.smartphone.entity.Order;
import com.vn.aptech.smartphone.entity.OrderDetails;
import com.vn.aptech.smartphone.entity.payload.request.OrderPayload;
import com.vn.aptech.smartphone.repository.CustomerRepository;
import com.vn.aptech.smartphone.repository.OrderRepository;
import com.vn.aptech.smartphone.service.OrderDetailsService;
import com.vn.aptech.smartphone.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDetailsService orderDetailsService;

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;


    public Order createOrder(OrderPayload orderPayload) {
        Customer customer = createCustomer(orderPayload);

        Order order = new Order(
                false,
                0.0,
                orderPayload.getType_delivery(),
                ZonedDateTime.now(ZoneOffset.UTC),
                ZonedDateTime.now(ZoneOffset.UTC),
                customer,
                null

        );

        order = orderRepository.save(order);

        List<OrderDetails> orderDetails = orderDetailsService.createOrderDetail(orderPayload.
                getOrderDetailsPayloadList(), order);

        double totalAmount = calculatorAmount(orderDetails);
        updateOrder(order, orderDetails, totalAmount);

        return order;
    }

    private double calculatorAmount(List<OrderDetails> orderDetails) {
        return orderDetails.stream()
                .mapToDouble(OrderDetails::getAmount)
                .sum();
    }


    private Customer createCustomer(OrderPayload orderPayload) {
        Customer customer = Customer.builder()
                .address(orderPayload.getAddress())
                .phone(orderPayload.getPhone())
                .email(orderPayload.getEmail())
                .fullName(orderPayload.getFullName())
                .description(orderPayload.getDescription() == null ? "Customer" : orderPayload.getDescription())
                .build();
        return customerRepository.save(customer);
    }

    private void updateOrder(Order order, List<OrderDetails> orderDetails, double totalAmount) {
        order.setUpdateTime(ZonedDateTime.now());
        order.setOrderDetails(orderDetails);
        order.setTotalPrice(totalAmount);
        orderRepository.save(order);
    }

}
