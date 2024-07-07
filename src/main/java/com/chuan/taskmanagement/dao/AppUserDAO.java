package com.chuan.taskmanagement.dao;

import com.chuan.taskmanagement.entity.AppUser;
import com.chuan.taskmanagement.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AppUserDAO {

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser save(AppUser appUser) {
        if (Objects.isNull(appUser)) {
            return null;
        }
        return appUserRepository.save(appUser);
    }

    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email).orElse(null);
    }

    public List<AppUser> findAllByEmail(List<String> emailList) {
        return appUserRepository.findAllByEmail(emailList);
    }

    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }
}
