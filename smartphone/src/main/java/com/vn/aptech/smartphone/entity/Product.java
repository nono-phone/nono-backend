package com.vn.aptech.smartphone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product extends BaseEntity {
    @Column(nullable = false, length = 128, unique = true)
    @NotNull
    @Length(min = 5, max = 128)
    private String name;
    private Double price;
    private String description;
    private String image;
    @Column(columnDefinition = "tinyint(1)")
    private boolean isEnable = true;
    private int quantity;

    @ManyToOne
    @JoinColumn(name="category_id")
    @JsonProperty(value = "categories")
    private Category categories;



    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private List<OrderDetails> orderDetails;
}