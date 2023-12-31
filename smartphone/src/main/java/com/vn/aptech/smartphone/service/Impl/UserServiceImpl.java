package com.vn.aptech.smartphone.service.Impl;

import com.vn.aptech.smartphone.common.Role;
import com.vn.aptech.smartphone.dto.UserDto;
import com.vn.aptech.smartphone.dto.UserResponseDto;
import com.vn.aptech.smartphone.entity.SafeguardUser;
import com.vn.aptech.smartphone.entity.payload.request.InfoPayload;
import com.vn.aptech.smartphone.entity.payload.request.RegisterPayload;
import com.vn.aptech.smartphone.entity.payload.request.ResetPasswordPayload;
import com.vn.aptech.smartphone.entity.payload.request.UpdateRolePayload;
import com.vn.aptech.smartphone.exception.ConflictException;
import com.vn.aptech.smartphone.exception.NotFoundException;
import com.vn.aptech.smartphone.repository.UserRepository;
import com.vn.aptech.smartphone.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            safeguardUser.setName(infoPayload.getName());
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
    public UserDto updateUserByAdmin(UserDto userDto, Long id) {
        SafeguardUser user = findUserById(id);
        user.setName(userDto.getName());
        user.setStatus(userDto.getStatus());
        user.setGender(userDto.getGender());
        user.setPhone(userDto.getPhone());
        user.setImage(userDto.getImage());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setRole(userDto.getRole());
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public List<SafeguardUser> addListUser(List<SafeguardUser> users) {
        List<SafeguardUser> savedUsers = new ArrayList<>();
        for (SafeguardUser user: users) {
            checkConflictUserEmail(user.getEmail());
            SafeguardUser safeguardUser = createSafeguardUser(user);
            SafeguardUser saved = userRepository.save(safeguardUser);
            savedUsers.add(saved);
        }
        return savedUsers;
    }

//    @Override
//    public SafeguardUser add(SafeguardUser user) {
//        checkConflictUserEmail(user.getEmail());
//        SafeguardUser safeguardUser = createSafeguardUser(user);
//        SafeguardUser saved = userRepository.save(safeguardUser);
//        cleanLoginAttempt(registerPayload.getEmail());
//
//        return modelMapper.map(saved, UserResponseDto.class);
//    }

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
    private SafeguardUser createSafeguardUser(@NotNull SafeguardUser user) {
        SafeguardUser createUser = new SafeguardUser();
        createUser.setDateOfBirth(user.getDateOfBirth());
        createUser.setImage(user.getImage());
        createUser.setGender(user.getGender());
        createUser.setPhone(user.getPhone());
        createUser.setEmail(user.getEmail());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.setRole(user.getRole());
        createUser.setStatus(true);

        if(user.getName() == null || user.getName().isEmpty()) {
            String [] str = user.getEmail().split("@");
            createUser.setName(str[0]);
        }else {
            createUser.setName(user.getName());
        }

        return createUser;
    }
    private SafeguardUser findUserById(Long userId) {
        SafeguardUser user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("Not found user with id = %d", userId))
        );
        return user;
    }
    private void checkConflictUserEmail(String email) throws ConflictException {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException(String.format("%s is already in use", email));
        }
    }
}
