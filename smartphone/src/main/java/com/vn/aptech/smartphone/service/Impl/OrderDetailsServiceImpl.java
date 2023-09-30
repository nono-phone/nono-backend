package com.vn.aptech.smartphone.service.Impl;

import com.vn.aptech.smartphone.entity.Order;
import com.vn.aptech.smartphone.entity.OrderDetails;
import com.vn.aptech.smartphone.entity.Product;
import com.vn.aptech.smartphone.entity.payload.request.OrderDetailsPayload;
import com.vn.aptech.smartphone.exception.InsufficientStockException;
import com.vn.aptech.smartphone.exception.NotFoundException;
import com.vn.aptech.smartphone.exception.NotFoundListProductException;
import com.vn.aptech.smartphone.repository.OrderDetailsRepository;
import com.vn.aptech.smartphone.repository.ProductRepository;
import com.vn.aptech.smartphone.service.OrderDetailsService;
import com.vn.aptech.smartphone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final ProductRepository productRepository;

    private final OrderDetailsRepository orderDetailsRepository;

    public List<OrderDetails> createOrderDetail(List<OrderDetailsPayload> orderDetailsPayloadList, Order order) {

        List<OrderDetails> orderDetails = orderDetailsPayloadList.stream()
                .map(orderDetail -> {
                    Product product = productRepository.findById(orderDetail.getProductId()).orElseThrow();
                    double totalPrice = orderDetail.getQuantity() * product.getPrice();
                    return new OrderDetails(orderDetail.getQuantity(), product.getPrice(), totalPrice, order, product);
                })
                .collect(Collectors.toList());

        orderDetailsRepository.saveAll(orderDetails);

        return orderDetails;

//        List<OrderDetails> orderDetails = new ArrayList<>();
//        List<String> productsIdNotFound = new ArrayList<>();
//
//
//        for (OrderDetailsPayload or: orderDetailsPayloadList) {
//            Optional<Product> product = productRepository.findById(or.getProductId());
//            if(product.isEmpty()) {
//                productsIdNotFound.add(or.getProductId().toString());
//            }
//        }
//        if(productsIdNotFound.isEmpty()) {
//            for (OrderDetailsPayload orderDetail : orderDetailsPayloadList) {
//                Optional<Product> product = productRepository.findById(orderDetail.getProductId());
//                if(!product.isPresent())
//
//                    if (product.isPresent()) {
//                        OrderDetails mapOrderDetail = new OrderDetails(
//                                orderDetail.getQuantity(),
//                                product.get().getPrice(),
//                                orderDetail.getQuantity() * product.get().getPrice(),
//                                order,
//                                product.get()
//                        );
//                        orderDetails.add(mapOrderDetail);
//                    }
//
//            }
//        }else {
//            throw new NotFoundListProductException(productsIdNotFound);
//        }
//
//        orderDetailsRepository.saveAll(orderDetails);
//
//        return orderDetails;
    }

}
