package com.vn.aptech.smartphone.repository;

import com.vn.aptech.smartphone.entity.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {
    //check xem user login nhieu lan
    Optional<LoginAttempt> findByEmail(String email);
}
