package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.annotation.UserPrincipal;
import com.vn.aptech.smartphone.dto.ErrorResponse;
import com.vn.aptech.smartphone.dto.UserLoginDto;
import com.vn.aptech.smartphone.dto.response.RefreshTokenResponse;
import com.vn.aptech.smartphone.dto.response.UserResponseDto;
import com.vn.aptech.smartphone.entity.payload.request.LoginPayload;
import com.vn.aptech.smartphone.entity.payload.request.RegisterPayload;
import com.vn.aptech.smartphone.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ProblemDetail;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "Responsible for handling user authentication-related operations")
public class AuthController {
    private final AuthService AuthService;

    @Operation(summary = "Login by providing email & password credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generate a refresh token and a access token",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserLoginDto.class))}),
            @ApiResponse(responseCode = "400", description = "The provided email or password format is invalid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "401", description = "Invalid email or password credentials",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "422", description = "Failed login more than 5 times",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping(value = "/login")
    public ResponseEntity<UserLoginDto> login(@RequestBody @Valid LoginPayload loginPayload) {
        return ResponseEntity.ok()
                .body(AuthService.login(loginPayload));
    }

    @Operation(summary = "Register by providing necessary registration details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered user details",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "The provided email or password format is invalid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
    })
    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid RegisterPayload register) {
        return ResponseEntity.ok(AuthService.register(register));
    }

    @Operation(summary = "Generates a new access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generate a new access token",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RefreshTokenResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token credentials",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @SecurityRequirement(name = "refresh_token")
    @GetMapping(value = "/refresh", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_REFRESH') or hasRole('REFRESH')")
    public ResponseEntity<RefreshTokenResponse> refresh() {
        return ResponseEntity.ok(AuthService.refresh());
    }

    @Operation(summary = "Sign out the refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Add the token into black list", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token credentials",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @SecurityRequirement(name = "refresh_token")
    @DeleteMapping("/logout")
    @PreAuthorize("hasAuthority('ROLE_REFRESH') or hasRole('REFRESH')")
    public ResponseEntity<Void> logout(@UserPrincipal UserDetails currentUser,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION)  String refreshToken) {
        AuthService.logout(currentUser, refreshToken);
        return ResponseEntity.noContent().build();
    }

}
