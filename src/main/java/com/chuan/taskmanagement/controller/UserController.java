package com.chuan.taskmanagement.controller;

import com.chuan.taskmanagement.dto.DropdownResponse;
import com.chuan.taskmanagement.dto.user.ProfileRequest;
import com.chuan.taskmanagement.dto.user.ProfileResponse;
import com.chuan.taskmanagement.dto.user.ResetPasswordRequest;
import com.chuan.taskmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path = "user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/dropdown")
    public ResponseEntity<List<DropdownResponse>> dropdown() {
        return ResponseEntity.ok(userService.getDropdown());
    }

    @PutMapping("my-profile")
    public ResponseEntity<ProfileResponse> update(@RequestBody ProfileRequest profileRequest) {
        return ResponseEntity.ok(userService.updateMyProfile(profileRequest));
    }

    @GetMapping("my-profile")
    public ResponseEntity<ProfileResponse> update() {
        return ResponseEntity.ok(userService.readMyProfile());
    }

    @PutMapping("reset-password")
    public ResponseEntity<?> update(@RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request);
        return ResponseEntity.ok().build();
    }
}
