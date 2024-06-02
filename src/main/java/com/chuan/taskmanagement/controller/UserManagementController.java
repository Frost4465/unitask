package com.chuan.taskmanagement.controller;

import com.chuan.taskmanagement.exception.ServiceAppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(path = "user-management")
public class UserManagementController {

    @PostMapping("/test")
    public ResponseEntity<String> ping() {
        throw new ServiceAppException(HttpStatus.BAD_REQUEST, "TEST");
    }
}
