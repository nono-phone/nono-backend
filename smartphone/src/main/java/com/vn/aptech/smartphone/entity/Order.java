package com.vn.aptech.smartphone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vn.aptech.smartphone.common.DeliveryMethod;
import com.vn.aptech.smartphone.common.PaymentType;
import com.vn.aptech.smartphone.common.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@Builder
public class Order extends BaseEntity {
    private boolean isApproved;
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "type_delivery")
    private DeliveryMethod typeDeliveryMethod;
    private String deliveryAddress;


    private String reasonReject;

    private boolean isPayment;
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private Status orderStatus;

    private LocalDateTime onCreate;
    private LocalDateTime onUpdate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SafeguardUser user;


    @ManyToOne
    @JoinColumn(name = "staff_id")

    private SafeguardUser staff;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.PERSIST)
//    @JsonProperty(value = "order_details")
    @JoinColumn(name = "order_id")
    private List<OrderDetails> orderDetails;
}
