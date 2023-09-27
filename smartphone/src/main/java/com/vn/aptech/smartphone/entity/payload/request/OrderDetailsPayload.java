package com.vn.aptech.smartphone.entity.payload.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsPayload {
    private Long product_id;
    private int quantity;
    private double price;
}
