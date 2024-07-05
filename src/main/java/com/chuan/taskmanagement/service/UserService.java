package com.chuan.taskmanagement.service;

import com.chuan.taskmanagement.dto.DropdownResponse;

import java.util.List;

public interface UserService {
    void addUser(String username, String password, String name);

    List<DropdownResponse> getDropdown();
}
