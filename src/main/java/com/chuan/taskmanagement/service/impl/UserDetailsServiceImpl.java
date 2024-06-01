package com.chuan.taskmanagement.service.impl;

import com.chuan.taskmanagement.entity.AppUser;
import com.chuan.taskmanagement.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = userRepository.findByEmail(username).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("No user with this user name");
        } else {
            System.out.println(user.getEmail());
        }

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }

}