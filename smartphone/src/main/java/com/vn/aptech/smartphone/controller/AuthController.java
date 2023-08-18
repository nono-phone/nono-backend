package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.annotation.UserPrincipal;
import com.vn.aptech.smartphone.dto.response.RefreshTokenResponse;
import com.vn.aptech.smartphone.dto.response.UserResponse;
import com.vn.aptech.smartphone.entity.payload.request.LoginPayload;
import com.vn.aptech.smartphone.entity.payload.request.RegisterPayload;
import com.vn.aptech.smartphone.entity.payload.response.AuthenticationResponse;
import com.vn.aptech.smartphone.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginPayload loginPayload) {
        return ResponseEntity.ok()
                .body(authService.login(loginPayload));
    }

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterPayload register) {
        return ResponseEntity.ok(authService.register(register));
    }

    @GetMapping(value = "/refresh", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('USER') or hasRole('USER')")
    public ResponseEntity<RefreshTokenResponse> refresh() {
        return ResponseEntity.ok(authService.refresh());
    }

    @DeleteMapping("/logout")
    @PreAuthorize("hasAuthority('USER') or hasRole('USER')")
    public ResponseEntity<Void> logout(@UserPrincipal UserDetails currentUser,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION)  String refreshToken) {
        authService.logout(currentUser, refreshToken);
        return ResponseEntity.noContent().build();
    }

}
