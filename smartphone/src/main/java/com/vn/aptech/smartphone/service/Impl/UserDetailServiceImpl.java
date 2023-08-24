package com.vn.aptech.smartphone.service.Impl;

import com.vn.aptech.smartphone.entity.SafeguardUser;
import com.vn.aptech.smartphone.repository.UserRepository;
import com.vn.aptech.smartphone.security.BasicUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     SafeguardUser user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username not found: %s", username)));
        return BasicUserDetails.create(user);
    }
}
