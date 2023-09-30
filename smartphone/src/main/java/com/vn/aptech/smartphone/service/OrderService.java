package com.vn.aptech.smartphone.service;

import com.vn.aptech.smartphone.entity.Order;
import com.vn.aptech.smartphone.entity.payload.request.OrderPayload;
import com.vn.aptech.smartphone.entity.payload.request.OrderUpdatePayload;
import com.vn.aptech.smartphone.security.AppUserDetails;

public interface OrderService {
    Order createOrder(OrderPayload orderPayload);
    Order createOrderByUser(AppUserDetails currentUser, OrderPayload orderPayload);
    void approvedOrder(Long idOrder, AppUserDetails appUserDetails);

    Order updateOrder(Long idOrder, AppUserDetails userDetails, OrderUpdatePayload orderPayload);

}
