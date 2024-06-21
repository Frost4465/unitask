package com.chuan.taskmanagement.dao;

import com.chuan.taskmanagement.entity.AppUser;
import com.chuan.taskmanagement.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserDAO {

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email).orElse(null);
    }

}
