package com.vn.aptech.smartphone.service;

import com.vn.aptech.smartphone.entity.SafeguardUser;
import com.vn.aptech.smartphone.entity.payload.request.InfoPayload;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    void update(UserDetails userDetails, InfoPayload infoPayload);
    List<SafeguardUser> getAll();
}
