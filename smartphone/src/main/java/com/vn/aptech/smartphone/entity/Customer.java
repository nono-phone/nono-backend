package com.vn.aptech.smartphone.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends BaseEntity {
    @NotNull
    private String fullName;
    @NotNull
    private String address;
    @NotNull
    private String phone;
    @NotNull
    @Email
    private String email;
    private String description;
}
