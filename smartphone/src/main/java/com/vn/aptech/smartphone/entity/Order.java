package com.vn.aptech.smartphone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vn.aptech.smartphone.common.Delivery;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private boolean isApproved;
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "type_delivery")
    private Delivery typeDelivery;
    private ZonedDateTime createTime;
    private ZonedDateTime updateTime;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.PERSIST)
    @JsonProperty(value = "order_details")
    @JoinColumn(name = "order_id")
    private List<OrderDetails> orderDetails;
}
