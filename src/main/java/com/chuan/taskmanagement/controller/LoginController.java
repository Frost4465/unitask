package com.chuan.taskmanagement.controller;

import com.chuan.taskmanagement.dto.user.LoginRequest;
import com.chuan.taskmanagement.dto.user.SignUpRequest;
import com.chuan.taskmanagement.security.JwtUtills;
import com.chuan.taskmanagement.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(path = "auth")
public class LoginController {

    @Autowired
    private JwtUtills utils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = utils.generateJwtToken(authentication);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> singUp(@RequestBody SignUpRequest signUpRequest) {
        userService.addUser(signUpRequest.getUsername(), signUpRequest.getPassword(), signUpRequest.getName());
        return ResponseEntity.ok("SIGN UP");
    }

    //TODO oauth
}
