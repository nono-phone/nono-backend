package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.annotation.UserPrincipal;
import com.vn.aptech.smartphone.entity.SafeguardUser;
import com.vn.aptech.smartphone.entity.payload.request.InfoPayload;
import com.vn.aptech.smartphone.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    //add list user
    private final IUserService IUserService;
    @PutMapping(value = "/update-info")
    public ResponseEntity<Void> updateInfo(@UserPrincipal UserDetails currentUser,
                                     @RequestBody @Valid InfoPayload infoPayload) {
        IUserService.update(currentUser, infoPayload);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<List<SafeguardUser>> get(){
        return ResponseEntity.ok(IUserService.getAll());
    }
    //add user

    //update user

    //disable user


}
