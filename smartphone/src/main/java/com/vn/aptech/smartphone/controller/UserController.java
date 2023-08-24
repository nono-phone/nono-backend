package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.annotation.UserPrincipal;
import com.vn.aptech.smartphone.entity.payload.request.InfoPayload;
import com.vn.aptech.smartphone.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    //add list user
    private final UserService userService;
    @PutMapping(value = "/update-info")
    public ResponseEntity<Void> updateInfo(@UserPrincipal UserDetails currentUser,
                                     @RequestBody @Valid InfoPayload infoPayload) {
        userService.update(currentUser, infoPayload);
        return ResponseEntity.ok().build();
    }
    //add user

    //update user

    //disable user


}
