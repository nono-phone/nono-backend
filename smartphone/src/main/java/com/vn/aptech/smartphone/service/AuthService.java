package com.vn.aptech.smartphone.service;

import com.vn.aptech.smartphone.dto.response.RefreshTokenResponse;
import com.vn.aptech.smartphone.dto.response.UserResponse;
import com.vn.aptech.smartphone.entity.payload.request.LoginPayload;
import com.vn.aptech.smartphone.entity.payload.request.RegisterPayload;
import com.vn.aptech.smartphone.entity.payload.response.AuthenticationResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    UserResponse register(RegisterPayload registerPayload);

    AuthenticationResponse login(LoginPayload loginPayload);

    RefreshTokenResponse refresh();

    void logout(UserDetails currUser, String refreshToken);
}
