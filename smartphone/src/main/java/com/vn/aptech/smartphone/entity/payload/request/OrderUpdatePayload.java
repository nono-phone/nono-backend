package com.vn.aptech.smartphone.entity.payload.request;

import com.vn.aptech.smartphone.common.DeliveryMethod;
import com.vn.aptech.smartphone.common.PaymentType;
import com.vn.aptech.smartphone.common.Status;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdatePayload {
    @NotEmpty
    private boolean isApproved;
    @NotEmpty
    private boolean isPayment;
    @NotEmpty
    private Status orderStatus;
    @NotEmpty
    private PaymentType paymentType;
    @NotEmpty
    private String reasonReject;
    @NotEmpty
    private double totalPrice;
    @NotEmpty
    private DeliveryMethod deliveryMethod;
    @NotEmpty
    private String deliveryAddress;

}
