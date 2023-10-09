package com.vn.aptech.smartphone.dto;

import com.vn.aptech.smartphone.common.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
    private Long id;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String phone;
    private String dateOfBirth;
    private String gender;
    private String image;
    private Boolean status;
    private String name;
}
