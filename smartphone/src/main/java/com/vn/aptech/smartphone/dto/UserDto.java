package com.vn.aptech.smartphone.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vn.aptech.smartphone.common.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String dateOfBirth;
    private String gender;
    private String image;
    private Boolean status;
}
