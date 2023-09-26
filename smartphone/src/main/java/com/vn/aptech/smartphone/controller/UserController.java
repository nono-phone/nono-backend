package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.annotation.UserPrincipal;
import com.vn.aptech.smartphone.dto.UserDto;
import com.vn.aptech.smartphone.entity.SafeguardUser;
import com.vn.aptech.smartphone.entity.payload.request.InfoPayload;
import com.vn.aptech.smartphone.entity.payload.request.ResetPasswordPayload;
import com.vn.aptech.smartphone.entity.payload.request.UpdateRolePayload;
import com.vn.aptech.smartphone.security.AppUserDetails;
import com.vn.aptech.smartphone.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @SecurityRequirement(name = "access_token")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/get-all")
    public ResponseEntity<List<UserDto>> get(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
    @SecurityRequirement(name = "access_token")
    @GetMapping
    public ResponseEntity<UserDto> getById (@RequestParam(name = "id") Long idUser) {
        return new ResponseEntity<>(userService.getById(idUser), HttpStatus.OK);
    }

    @SecurityRequirement(name = "access_token")
    @PutMapping(value = "/update-info")
    public ResponseEntity<UserDto> updateInfo(@UserPrincipal UserDetails currentUser,
                                     @RequestBody @Valid InfoPayload infoPayload) {
        return new ResponseEntity<>(userService.update(currentUser, infoPayload), HttpStatus.OK);
    }

//    @PutMapping(value = "/udpate-user")
//    public ResponseEntity<?> updateUser() {
//        return new ResponseEntity<>(userService.updateUser(currentUser, inf))
//    }

//    @PostMapping
//    public ResponseEntity<UserDto> createUser (@RequestBody )


    //update user

    //disable user
//    @PutMapping(value = "/update-user")
//    public ResponseEntity<UserDto> updateUser(UserDto userDto) {
//        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
//    }

    @SecurityRequirement(name = "access_token")
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyProfiles(@UserPrincipal AppUserDetails currentUser) {
        if(currentUser != null) {
            SafeguardUser user = currentUser.getSafeguardUser();
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return ResponseEntity.noContent().build();
    }

    @SecurityRequirement(name = "access_token")
    @PutMapping(value = "/disable-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> updateUser(@RequestParam(name = "id") Long idUser) {
        userService.disableUser(idUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SecurityRequirement(name = "access_token")
    @PutMapping("{userId}/password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateUserPassword(@PathVariable("userId") Long userId,
                                                   @Valid @RequestBody ResetPasswordPayload payload) {
        userService.updatePasswordUser(userId, payload);
        return ResponseEntity.ok().build();
    }

    @SecurityRequirement(name = "access_token")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{userId}")
    public ResponseEntity<UserDto> updateRoleUser(@PathVariable("userId") Long userId,
                                               @Valid @RequestBody UpdateRolePayload payload) {
        userService.updateRoleUser(userId, payload);
        return ResponseEntity.ok().build();
    }
}
