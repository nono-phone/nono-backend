package com.vn.aptech.smartphone.entity.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vn.aptech.smartphone.common.DeliveryMethod;
import com.vn.aptech.smartphone.common.PaymentType;
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
    private DeliveryMethod type_delivery;
    private PaymentType paymentType;
    private boolean isPayment;
    @JsonProperty(value = "order_details")
    private List<OrderDetailsPayload> orderDetailsPayloadList;
}
