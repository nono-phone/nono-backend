package com.vn.aptech.smartphone.service.Impl;

import com.vn.aptech.smartphone.common.Status;
import com.vn.aptech.smartphone.entity.*;
import com.vn.aptech.smartphone.entity.payload.request.OrderDetailsPayload;
import com.vn.aptech.smartphone.entity.payload.request.OrderPayload;
import com.vn.aptech.smartphone.entity.payload.request.OrderUpdatePayload;
import com.vn.aptech.smartphone.exception.InsufficientStockException;
import com.vn.aptech.smartphone.exception.NotFoundException;
import com.vn.aptech.smartphone.exception.NotFoundListProductException;
import com.vn.aptech.smartphone.repository.CustomerRepository;
import com.vn.aptech.smartphone.repository.OrderRepository;
import com.vn.aptech.smartphone.repository.ProductRepository;
import com.vn.aptech.smartphone.security.AppUserDetails;
import com.vn.aptech.smartphone.service.OrderDetailsService;
import com.vn.aptech.smartphone.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDetailsService orderDetailsService;

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public Order createOrder(OrderPayload orderPayload) {
        Customer customer = createCustomer(orderPayload);

        Order order = Order.builder()
                .customer(customer)
                .isApproved(false)
                .onCreate(LocalDateTime.now())
                .onUpdate(LocalDateTime.now())
                .totalPrice(0.0)
                .orderStatus(Status.PENDING_APPROVAL)
                .typeDeliveryMethod(orderPayload.getType_delivery())
                .paymentType(orderPayload.getPaymentType())
                .deliveryAddress(orderPayload.getAddress())
                .isPayment(orderPayload.isPayment())
                .user(null)
                .staff(null)
                .build();

        List<String> productsIdNotFound = orderPayload.getOrderDetailsPayloadList().stream()
                .map(OrderDetailsPayload::getProductId)
                .filter(productId -> productRepository.findById(productId).isEmpty())
                .map(Object::toString)
                .collect(Collectors.toList());

        if (!productsIdNotFound.isEmpty()) {
            throw new NotFoundListProductException(productsIdNotFound);
        }

        List<String> products = checkStock(orderPayload.getOrderDetailsPayloadList());
        if(!products.isEmpty()) {
            throw new InsufficientStockException(products);
        }


        order = orderRepository.save(order);

        List<OrderDetails> orderDetails = orderDetailsService.createOrderDetail(orderPayload.
                getOrderDetailsPayloadList(), order);

        double totalAmount = calculatorAmount(orderDetails);
        order = updateOrder(order, orderDetails, totalAmount);

        return order;
    }

    @Override
    public Order createOrderByUser(AppUserDetails currentUser, OrderPayload orderPayload) {
        return null;
    }

    @Override
    public void approvedOrder(Long idOrder, AppUserDetails appUserDetails) {
        SafeguardUser user = appUserDetails.getSafeguardUser();
        Order order = findByIdOrder(idOrder);
        order.setApproved(true);
        order.setOnUpdate(LocalDateTime.now());
        order.setStaff(user);
        order.setOrderStatus(Status.PROCESSING);
        orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long idOrder, AppUserDetails userDetails, OrderUpdatePayload orderPayload) {
       Order order = findByIdOrder(idOrder);
        if (orderPayload.isApproved() != order.isApproved()) {
            order.setApproved(orderPayload.isApproved());
        }

        if (orderPayload.isPayment() != order.isPayment()) {
            order.setPayment(orderPayload.isPayment());
        }
        updateField(orderPayload.getOrderStatus(), order::setOrderStatus, order::getOrderStatus);
        updateField(orderPayload.getPaymentType(), order::setPaymentType, order::getPaymentType);
        updateField(orderPayload.getReasonReject(), order::setReasonReject, order::getReasonReject);
        updateField(orderPayload.getTotalPrice(), order::setTotalPrice, order::getTotalPrice);
        updateField(orderPayload.getDeliveryMethod(), order::setTypeDeliveryMethod, order::getTypeDeliveryMethod);
        updateField(orderPayload.getDeliveryAddress(), order::setDeliveryAddress, order::getDeliveryAddress);

        order.setOnUpdate(LocalDateTime.now());
        order.setStaff(userDetails.getSafeguardUser());
        order = orderRepository.save(order);

        return order;
    }

    private <T> void updateField(T newValue, Consumer<T> setter, Supplier<T> getter) {
        if (newValue != null && !newValue.equals(getter.get())) {
            setter.accept(newValue);
        }
    }
    private Order findByIdOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(
                ()-> new NotFoundException(String.format("Not found order with id = %d", id))
        );
    }
    private double calculatorAmount(List<OrderDetails> orderDetails) {
        return orderDetails.stream()
                .mapToDouble(OrderDetails::getAmount)
                .sum();
    }


    private Customer createCustomer(OrderPayload orderPayload) {
        Optional<Customer> customer = customerRepository.findCustomerByEmail(orderPayload.getEmail());
        if(customer.isEmpty()) {
            Customer savedCustomer = Customer.builder()
                    .address(orderPayload.getAddress())
                    .phone(orderPayload.getPhone())
                    .email(orderPayload.getEmail())
                    .fullName(orderPayload.getFullName())
                    .description(orderPayload.getDescription() == null ? "Customer" : orderPayload.getDescription())
                    .build();
            return customerRepository.save(savedCustomer);
        }else {
            return customer.get();
        }

    }

    private Order updateOrder(Order order, List<OrderDetails> orderDetails, double totalAmount) {
        order.setOnUpdate(LocalDateTime.now());
        order.setOrderDetails(orderDetails);
        order.setTotalPrice(totalAmount);
        return orderRepository.save(order);
    }

    private List<String> checkStock(List<OrderDetailsPayload> orderDetailsPayloadList) {

        List<Product> products = new ArrayList<>();
        List<String> productsStock = new ArrayList<>();
        Map<Long, Integer> orderDetailsMap = orderDetailsPayloadList.stream()
                .collect(Collectors.toMap(
                        OrderDetailsPayload::getProductId,
                        OrderDetailsPayload::getQuantity,
                        Integer::sum
                ));

        for(OrderDetailsPayload order: orderDetailsPayloadList) {
            products.add(productRepository.findById(order.getProductId()).get());
        }

        for(Product pro: products) {
            int  input = orderDetailsMap.get(pro.getId());
            if(input > pro.getQuantity()) {
                productsStock.add(pro.getId().toString());
            }
        }
        return productsStock;
    }

}
