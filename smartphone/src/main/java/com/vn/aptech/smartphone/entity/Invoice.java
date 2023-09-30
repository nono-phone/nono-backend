//package com.vn.aptech.smartphone.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.Instant;
//import java.util.List;
//
//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@Entity
//@Table(name = "invoice")
//public class Invoice extends BaseEntity {
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    private SafeguardUser user;
//    private double totalPrice;
//    private Instant createTime;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    private SafeguardUser createBy;
//
//    private boolean isPayment;
//    private String paymentType;
//
//    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "invoice_id")
//    private List<InvoiceDetails> invoiceDetails;
//}
