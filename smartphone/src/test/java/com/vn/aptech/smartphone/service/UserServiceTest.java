package com.vn.aptech.smartphone.service;

import com.vn.aptech.smartphone.dto.UserDto;
import com.vn.aptech.smartphone.entity.SafeguardUser;
import com.vn.aptech.smartphone.repository.UserRepository;
import com.vn.aptech.smartphone.service.Impl.UserServiceImpl;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void getListUserTest() {
        SafeguardUser user1 = new SafeguardUser();
        SafeguardUser user2= new SafeguardUser();

        List<UserDto> userDtos = Stream.of(user1, user2)
                .map( (user)->modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

        when(userRepository.findAll().stream().map(
                (user)->modelMapper.map(user, UserDto.class)).collect(Collectors.toList())
        ).thenReturn(userDtos);

        assertEquals(userService.getAll(), userDtos);
    }

}
