package com.vn.aptech.smartphone.entity.payload.response;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String refreshToken;
    private String accessToken;
}
