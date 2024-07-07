package com.chuan.taskmanagement.service;

import com.chuan.taskmanagement.dto.DropdownResponse;
import com.chuan.taskmanagement.dto.user.ProfileRequest;
import com.chuan.taskmanagement.dto.user.ProfileResponse;
import com.chuan.taskmanagement.dto.user.ResetPasswordRequest;

import java.util.List;

public interface UserService {
    void addUser(String username, String password, String name);

    ProfileResponse readMyProfile();

    ProfileResponse updateMyProfile(ProfileRequest profileRequest);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);

    List<DropdownResponse> getDropdown();
}
