package com.vn.aptech.smartphone.entity.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsPayload {
    @JsonProperty(value = "product_id")
    private Long productId;
    private int quantity;
}
