package com.chuan.taskmanagement.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ContextService {

    public String getCurrentAuthUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
