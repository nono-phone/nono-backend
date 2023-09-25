package com.vn.aptech.smartphone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vn.aptech.smartphone.common.Delivery;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Delivery typeDelivery;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    private List<OrderDetails> orderDetails;
}
