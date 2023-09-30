package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.annotation.UserPrincipal;
import com.vn.aptech.smartphone.entity.Order;
import com.vn.aptech.smartphone.entity.payload.request.ApprovedPayload;
import com.vn.aptech.smartphone.entity.payload.request.OrderPayload;
import com.vn.aptech.smartphone.entity.payload.request.OrderUpdatePayload;
import com.vn.aptech.smartphone.security.AppUserDetails;
import com.vn.aptech.smartphone.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class OrderController {
    private final OrderService orderService;
    @SecurityRequirement(name = "access_token")
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderPayload orderPayload) {
        return new ResponseEntity<>(orderService.createOrder(orderPayload), HttpStatus.CREATED);
    }
    @SecurityRequirement(name = "access_token")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("approved/{id}")
    public ResponseEntity<Void> approved(@PathVariable(value = "id") Long idOrder,
                                         @UserPrincipal AppUserDetails userDetails) {
        orderService.approvedOrder(idOrder, userDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SecurityRequirement(name = "access_token")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable(value = "id") Long idOrder,
                                             @UserPrincipal AppUserDetails userDetails,
                                             @RequestBody OrderUpdatePayload orderUpdatePayload
    ) {
        Order order = orderService.updateOrder(idOrder, userDetails, orderUpdatePayload);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
