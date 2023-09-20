package com.vn.aptech.smartphone.service.Impl;

import com.vn.aptech.smartphone.dto.UserDto;
import com.vn.aptech.smartphone.entity.SafeguardUser;
import com.vn.aptech.smartphone.entity.payload.request.InfoPayload;
import com.vn.aptech.smartphone.entity.payload.request.ResetPasswordPayload;
import com.vn.aptech.smartphone.entity.payload.request.UpdateRolePayload;
import com.vn.aptech.smartphone.exception.NotFoundException;
import com.vn.aptech.smartphone.repository.UserRepository;
import com.vn.aptech.smartphone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto update(UserDetails userDetails, InfoPayload infoPayload) {
        Optional<SafeguardUser> user = userRepository.findByEmail(userDetails.getUsername());
        user.ifPresent(safeguardUser -> {
            safeguardUser.setImage(infoPayload.getImage());
            safeguardUser.setGender(infoPayload.getGender());
            //safeguardUser.setName(infoPayload.getName());
            safeguardUser.setPhone(infoPayload.getPhone());
            safeguardUser.setDateOfBirth(infoPayload.getDateOfBirth());
            userRepository.save(safeguardUser);
        });
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void disableUser(Long idUser) {
        SafeguardUser user = findUserById(idUser);
        if (user.getStatus()) {
            user.setStatus(false);
        } else {
            user.setStatus(true);
        }
        userRepository.save(user);
    }

    @Override
    public void updatePasswordUser(Long userId, ResetPasswordPayload resetPasswordPayload) {
        SafeguardUser user = findUserById(userId);
        user.setPassword(passwordEncoder.encode(resetPasswordPayload.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDto updateRoleUser(Long userId, UpdateRolePayload updateRolePayload) {
        SafeguardUser user = findUserById(userId);
        user.setRole(updateRolePayload.getRole());
        SafeguardUser saved = userRepository.save(user);
        return modelMapper.map(saved, UserDto.class);
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(
                        (user) -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Long idUser) {
        SafeguardUser user = findUserById(idUser);
        return modelMapper.map(user, UserDto.class);
    }
    private SafeguardUser findUserById(Long userId) {
        SafeguardUser user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("Not found user with id = %d", userId))
        );
        return user;
    }
}
