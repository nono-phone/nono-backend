package com.vn.aptech.smartphone.entity.payload.request;

import com.vn.aptech.smartphone.common.Delivery;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayload {
    private String fullName;
    private String address;
    private String phone;
    private String email;
    private String description;
    private Delivery type_delivery;
    private List<OrderDetailsPayload> orderDetailsPayloadList;
}
