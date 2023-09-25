package com.vn.aptech.smartphone.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vn.aptech.smartphone.common.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
