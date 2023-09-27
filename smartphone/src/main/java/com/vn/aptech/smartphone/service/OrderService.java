package com.vn.aptech.smartphone.service;

import com.vn.aptech.smartphone.entity.Order;
import com.vn.aptech.smartphone.entity.payload.request.OrderPayload;

public interface OrderService {
    Order createOrder(OrderPayload orderPayload);

}
