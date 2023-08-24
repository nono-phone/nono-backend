package com.vn.aptech.smartphone.service.Impl;

import com.vn.aptech.smartphone.common.Role;
import com.vn.aptech.smartphone.dto.response.RefreshTokenResponse;
import com.vn.aptech.smartphone.dto.response.UserResponse;
import com.vn.aptech.smartphone.entity.BlackListRefreshToken;
import com.vn.aptech.smartphone.entity.LoginAttempt;
import com.vn.aptech.smartphone.entity.SafeguardUser;
import com.vn.aptech.smartphone.repository.UserRepository;
import com.vn.aptech.smartphone.entity.payload.request.LoginPayload;
import com.vn.aptech.smartphone.entity.payload.request.RegisterPayload;
import com.vn.aptech.smartphone.entity.payload.response.AuthenticationResponse;
import com.vn.aptech.smartphone.exception.ConflictException;
import com.vn.aptech.smartphone.exception.LoginFailedException;
import com.vn.aptech.smartphone.repository.BlackListRefreshTokenRepository;
import com.vn.aptech.smartphone.repository.LoginAttemptRepository;
import com.vn.aptech.smartphone.security.access.AccessTokenProvider;
import com.vn.aptech.smartphone.security.refresh.RefreshTokenProvider;
import com.vn.aptech.smartphone.service.AuthService;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final LoginAttemptRepository loginAttemptRepository;
    private final RefreshTokenProvider refreshTokenProvider;
    private final AccessTokenProvider accessTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final BlackListRefreshTokenRepository blackListRefreshTokenRepository;

    @Value("${core.auth.refresh.expirationInMs}")
    private long refreshExpirationInMs;

    @Override
    public UserResponse register(RegisterPayload registerPayload) {
        checkConflictUserEmail(registerPayload.getEmail());
        SafeguardUser safeguardUser = createSafeguardUser(registerPayload);
        SafeguardUser saved = userRepository.save(safeguardUser);
        cleanLoginAttempt(registerPayload.getEmail());
        return new UserResponse(saved);
    }

    @Override
    public AuthenticationResponse login(LoginPayload loginPayload) {
        validateLoginAttempt(loginPayload.getEmail());
        validateStatusUser(loginPayload);
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginPayload.getEmail(), loginPayload.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String refreshToken = refreshTokenProvider.generateToken(authentication);
        String accessToken = accessTokenProvider.generateToken(authentication);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public RefreshTokenResponse refresh() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = accessTokenProvider.generateToken(authentication);
        return RefreshTokenResponse.builder().accessToken(accessToken).build();
    }

    @Override
    public void logout(UserDetails currUser, String refreshToken) {
        var token = createRefreshTokenBlackList(currUser.getUsername(), refreshToken);
        blackListRefreshTokenRepository.save(token);
    }

    private void checkConflictUserEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException(String.format("%s is already in use", email));
        }
    }

    private SafeguardUser createSafeguardUser(@NotNull RegisterPayload register) {
        SafeguardUser user = new SafeguardUser();
        user.setEmail(register.getEmail());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setRole(Role.USER);
        user.setStatus(true);
        return user;
    }

    private void validateLoginAttempt(String email) {
        Optional<LoginAttempt> byEmail = loginAttemptRepository.findByEmail(email);
        if (byEmail.isPresent() && byEmail.get().isLockout()) {
            throw new LoginFailedException(String.format("%s has been locked due to multiple failed login attempts", email));
        }
    }

    private void cleanLoginAttempt(String email) {
        Optional<LoginAttempt> byEmail = loginAttemptRepository.findByEmail(email);
        byEmail.ifPresent(loginAttemptRepository::delete);
    }

    private BlackListRefreshToken createRefreshTokenBlackList(String email, String refreshToken) {
        BlackListRefreshToken blackListRefreshToken = new BlackListRefreshToken();
        blackListRefreshToken.setEmail(email);
        blackListRefreshToken.setToken(refreshToken);
        Instant expirationDate = new Date().toInstant().plus(refreshExpirationInMs, ChronoUnit.MILLIS);
        blackListRefreshToken.setExpirationDate(expirationDate);
        return blackListRefreshToken;
    }

    private void validateStatusUser(LoginPayload loginPayload) {
        var user = userRepository.findByEmail(loginPayload.getEmail());
        if(user.isPresent() && !user.get().getStatus()) {
            throw new LoginFailedException(String.format("%s account is disabled", loginPayload.getEmail()));
        }
    }


}
