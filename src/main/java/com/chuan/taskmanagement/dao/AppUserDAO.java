package com.chuan.taskmanagement.dao;

import com.chuan.taskmanagement.constant.UserErrorConstant;
import com.chuan.taskmanagement.entity.AppUser;
import com.chuan.taskmanagement.exception.ServiceAppException;
import com.chuan.taskmanagement.repository.AppUserRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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

    public AppUser findById(Long id) {
        return appUserRepository.findById(id).orElseThrow(() ->
                new ServiceAppException(HttpStatus.BAD_REQUEST, UserErrorConstant.NOT_FOUND));
    }

    public List<AppUser> findByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return appUserRepository.findAllByIds(ids);
    }
}
