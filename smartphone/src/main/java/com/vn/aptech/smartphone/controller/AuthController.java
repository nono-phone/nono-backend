package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.annotation.UserPrincipal;
import com.vn.aptech.smartphone.dto.UserLoginDto;
import com.vn.aptech.smartphone.dto.response.RefreshTokenResponse;
import com.vn.aptech.smartphone.dto.response.UserResponseDto;
import com.vn.aptech.smartphone.entity.payload.request.LoginPayload;
import com.vn.aptech.smartphone.entity.payload.request.RegisterPayload;
import com.vn.aptech.smartphone.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService AuthService;

    @PostMapping(value = "/login")
    public ResponseEntity<UserLoginDto> login(@RequestBody @Valid LoginPayload loginPayload) {
        return ResponseEntity.ok()
                .body(AuthService.login(loginPayload));
    }

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid RegisterPayload register) {
        return ResponseEntity.ok(AuthService.register(register));
    }

    @GetMapping(value = "/refresh", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_REFRESH') or hasRole('REFRESH')")
    public ResponseEntity<RefreshTokenResponse> refresh() {
        return ResponseEntity.ok(AuthService.refresh());
    }

    @DeleteMapping("/logout")
    @PreAuthorize("hasAuthority('ROLE_REFRESH') or hasRole('REFRESH')")
    public ResponseEntity<Void> logout(@UserPrincipal UserDetails currentUser,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION)  String refreshToken) {
        AuthService.logout(currentUser, refreshToken);
        return ResponseEntity.noContent().build();
    }

}
