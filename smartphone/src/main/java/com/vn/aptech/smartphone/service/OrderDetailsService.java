package com.vn.aptech.smartphone.service;

import com.vn.aptech.smartphone.entity.Order;
import com.vn.aptech.smartphone.entity.OrderDetails;
import com.vn.aptech.smartphone.entity.payload.request.OrderDetailsPayload;

import java.util.List;

public interface OrderDetailsService {
    List<OrderDetails> createOrderDetail(List<OrderDetailsPayload> orderDetailsPayloadList, Order order);
}
