package com.chuan.taskmanagement.service.impl;

import com.chuan.taskmanagement.entity.AppUser;
import com.chuan.taskmanagement.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {


    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addUser(String username, String password) {
        AppUser appUser = new AppUser();
        appUser.setEmail(username);
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setRole("ADMIN");
        userRepository.save(appUser);
    }
}
