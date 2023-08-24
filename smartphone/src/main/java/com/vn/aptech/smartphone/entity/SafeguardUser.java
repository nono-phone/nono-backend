/*
 * Copyright (c) 2023 Mango Family
 * All rights reserved or may not! :)
 */

package com.vn.aptech.smartphone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vn.aptech.smartphone.common.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;


@Data
@Setter
@Getter
@ToString(callSuper = true, exclude = {"password"})
@EqualsAndHashCode(callSuper = true, exclude = {"password"})
//@Audited
@Entity
@Table(name = "safeguard_user")
public class SafeguardUser extends BaseEntity implements Serializable {

//    @Column(name = "username")
//    private String username;

  //  @Schema(example = "registered@dqtri.com")
    @Column(name = "email", length = 320, nullable = false, unique = true)
    private String email;

   // @Schema(example = "SUBMITTER")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 25, nullable = false)
    private Role role;

    //@Schema(hidden = true)
    @JsonIgnore
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    private String phone;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    private String gender;
    private String image;
    @NotNull
    private Boolean status;
    private String name;
}


