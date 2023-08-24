package com.vn.aptech.smartphone.service.Impl;

import com.vn.aptech.smartphone.entity.SafeguardUser;
import com.vn.aptech.smartphone.entity.payload.request.InfoPayload;
import com.vn.aptech.smartphone.repository.UserRepository;
import com.vn.aptech.smartphone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void update(UserDetails userDetails, InfoPayload infoPayload) {
        Optional<SafeguardUser> user = userRepository.findByEmail(userDetails.getUsername());
        user.ifPresent(safeguardUser -> {
            safeguardUser.setImage(infoPayload.getImage());
            safeguardUser.setGender(infoPayload.getGender());
            //safeguardUser.setName(infoPayload.getName());
            safeguardUser.setPhone(infoPayload.getPhone());
            safeguardUser.setDateOfBirth(infoPayload.getDateOfBirth());
            userRepository.save(safeguardUser);
        });
    }
}
