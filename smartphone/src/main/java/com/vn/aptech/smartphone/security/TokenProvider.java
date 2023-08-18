package com.vn.aptech.smartphone.security;


import org.springframework.security.core.Authentication;

public interface TokenProvider {
    String generateToken (Authentication authentication);
}
