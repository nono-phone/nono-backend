package com.vn.aptech.smartphone.dto;

import com.vn.aptech.smartphone.common.Role;
import com.vn.aptech.smartphone.entity.SafeguardUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private Role role;

//    public UserResponseDto(SafeguardUser appUser) {
//        this.id = appUser.getId();
//        this.email = appUser.getEmail();
//        this.role = appUser.getRole();
//    }

//    public static List<UserResponseDto> buildFromUsers(List<SafeguardUser> appUsers) {
//        return appUsers.stream().map(UserResponseDto::new).toList();
//    }
}