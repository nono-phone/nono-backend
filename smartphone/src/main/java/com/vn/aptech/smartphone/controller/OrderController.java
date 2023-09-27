package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.entity.payload.request.OrderPayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderPayload orderPayload) {
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }


}
