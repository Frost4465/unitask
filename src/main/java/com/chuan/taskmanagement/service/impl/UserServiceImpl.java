package com.chuan.taskmanagement.service.impl;

import com.chuan.taskmanagement.constant.AuthErrorConstant;
import com.chuan.taskmanagement.dao.AppUserDAO;
import com.chuan.taskmanagement.dto.DropdownResponse;
import com.chuan.taskmanagement.dto.user.ProfileRequest;
import com.chuan.taskmanagement.dto.user.ProfileResponse;
import com.chuan.taskmanagement.dto.user.ResetPasswordRequest;
import com.chuan.taskmanagement.entity.AppUser;
import com.chuan.taskmanagement.exception.ServiceAppException;
import com.chuan.taskmanagement.mapper.UserMapper;
import com.chuan.taskmanagement.service.ContextService;
import com.chuan.taskmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ContextService implements UserService {

    @Autowired
    private AppUserDAO appUserDAO;

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
        appUserDAO.save(appUser);
    }

    @Override
    public ProfileResponse readMyProfile() {
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        return userMapper.toResponse(appUser);
    }

    @Override
    public ProfileResponse updateMyProfile(ProfileRequest profileRequest) {
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        appUser.setName(profileRequest.getName());
        return userMapper.toResponse(appUserDAO.save(appUser));
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        if (appUser.getPassword().equals(passwordEncoder.encode(request.getOldPassword()))) {
            appUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
            appUserDAO.save(appUser);
        } else {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, AuthErrorConstant.INVALID_PASSWORD);
        }
    }

    @Override
    public List<DropdownResponse> getDropdown() {
        return userMapper.toDropdown(appUserDAO.findAll());
    }
}
