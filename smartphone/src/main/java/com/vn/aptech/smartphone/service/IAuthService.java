package com.vn.aptech.smartphone.service;

import com.vn.aptech.smartphone.dto.UserLoginDto;
import com.vn.aptech.smartphone.dto.response.RefreshTokenResponse;
import com.vn.aptech.smartphone.dto.response.UserResponseDto;
import com.vn.aptech.smartphone.entity.payload.request.LoginPayload;
import com.vn.aptech.smartphone.entity.payload.request.RegisterPayload;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthService {
    UserResponseDto register(RegisterPayload registerPayload);

    UserLoginDto login(LoginPayload loginPayload);

    RefreshTokenResponse refresh();

    void logout(UserDetails currUser, String refreshToken);
}
