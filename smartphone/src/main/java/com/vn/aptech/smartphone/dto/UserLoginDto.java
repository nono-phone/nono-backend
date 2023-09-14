package com.vn.aptech.smartphone.dto;

import com.vn.aptech.smartphone.common.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    private String accessToken;
    private String refreshToken;
    private String name;
    private String email;
    private String dateOfBirth;
    private String gender;
    private String image;
    private Role role;
}
