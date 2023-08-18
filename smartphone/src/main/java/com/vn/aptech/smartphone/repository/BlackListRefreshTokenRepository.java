package com.vn.aptech.smartphone.repository;

import com.vn.aptech.smartphone.entity.BlackListRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRefreshTokenRepository extends JpaRepository<BlackListRefreshToken, Long> {
    boolean existsByToken(String token);
}
