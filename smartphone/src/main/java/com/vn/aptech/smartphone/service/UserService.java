package com.vn.aptech.smartphone.service;

import com.vn.aptech.smartphone.dto.UserDto;
import com.vn.aptech.smartphone.entity.SafeguardUser;
import com.vn.aptech.smartphone.entity.payload.request.InfoPayload;
import com.vn.aptech.smartphone.entity.payload.request.ResetPasswordPayload;
import com.vn.aptech.smartphone.entity.payload.request.UpdateRolePayload;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    UserDto getById(Long idUser);
    UserDto update(UserDetails userDetails, InfoPayload infoPayload);
    void disableUser(Long idUser);
    void updatePasswordUser(Long userId, ResetPasswordPayload resetPasswordPayload);
    UserDto updateRoleUser(Long userId, UpdateRolePayload updateRolePayload);

//    SafeguardUser add (SafeguardUser user);
}
