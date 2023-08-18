package com.vn.aptech.smartphone.security;

import org.springframework.security.core.Authentication;

public interface TokenResolver {
    Authentication verifyToken (String token);
}
