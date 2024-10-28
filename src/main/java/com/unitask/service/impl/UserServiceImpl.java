package com.unitask.service.impl;

import com.unitask.constant.AuthErrorConstant;
import com.unitask.constant.UserErrorConstant;
import com.unitask.dao.AppUserDAO;
import com.unitask.dto.DropdownResponse;
import com.unitask.dto.user.ProfileRequest;
import com.unitask.dto.user.ProfileResponse;
import com.unitask.dto.user.ResetPasswordRequest;
import com.unitask.entity.AppUser;
import com.unitask.exception.ServiceAppException;
import com.unitask.mapper.UserMapper;
import com.unitask.service.ContextService;
import com.unitask.service.UserService;
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
        if (appUserDAO.findOptionalByEmail(username).isPresent()) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, UserErrorConstant.EXISTS);
        }

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
