package com.chuan.taskmanagement.controller;

import com.chuan.taskmanagement.dto.DropdownResponse;
import com.chuan.taskmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
