package com.vn.aptech.smartphone.service.Impl;

import com.vn.aptech.smartphone.entity.Order;
import com.vn.aptech.smartphone.entity.OrderDetails;
import com.vn.aptech.smartphone.entity.Product;
import com.vn.aptech.smartphone.entity.payload.request.OrderDetailsPayload;
import com.vn.aptech.smartphone.repository.OrderDetailsRepository;
import com.vn.aptech.smartphone.repository.ProductRepository;
import com.vn.aptech.smartphone.service.OrderDetailsService;
import com.vn.aptech.smartphone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final ProductService productService;

    private final OrderDetailsRepository orderDetailsRepository;
    public List<OrderDetails> createOrderDetail(List<OrderDetailsPayload> orderDetailsPayloadList, Order order) {
        List<OrderDetails> orderDetails = new ArrayList<>();

        for (OrderDetailsPayload orderDetail : orderDetailsPayloadList) {
            Product product = productService.getById(orderDetail.getProduct_id()).orElse(null);
            if (product != null) {
                double amount = calculatorAmount(orderDetail);
                OrderDetails mapOrderDetail = new OrderDetails(
                        orderDetail.getQuantity(),
                        orderDetail.getPrice(),
                        amount,
                        order,
                        product
                );
                orderDetails.add(mapOrderDetail);
            }
        }

        orderDetailsRepository.saveAll(orderDetails);

        return orderDetails;
    }

    private double calculatorAmount(OrderDetailsPayload orderDetails) {
        return orderDetails.getQuantity() * orderDetails.getPrice();
    }

}
