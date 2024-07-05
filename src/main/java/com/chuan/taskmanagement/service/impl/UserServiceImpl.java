package com.chuan.taskmanagement.service.impl;

import com.chuan.taskmanagement.dto.DropdownResponse;
import com.chuan.taskmanagement.entity.AppUser;
import com.chuan.taskmanagement.mapper.UserMapper;
import com.chuan.taskmanagement.repository.AppUserRepository;
import com.chuan.taskmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(String username, String password, String name) {
        AppUser appUser = new AppUser();
        appUser.setEmail(username);
        appUser.setName(name);
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setRole("ADMIN");
        userRepository.save(appUser);
    }

    @Override
    public List<DropdownResponse> getDropdown() {
        return userMapper.toDropdown(userRepository.findAll());
    }
}
