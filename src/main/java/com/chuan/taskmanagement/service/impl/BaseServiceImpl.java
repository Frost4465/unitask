package com.chuan.taskmanagement.service.impl;

import com.chuan.taskmanagement.security.JwtUtills;
import com.chuan.taskmanagement.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    private JwtUtills jwtUtills;

    public String getCurrentAuthUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
