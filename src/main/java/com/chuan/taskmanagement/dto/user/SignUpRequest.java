package com.chuan.taskmanagement.dto.user;

import lombok.Data;

@Data
public class SignUpRequest {

    private String username;

    private String name;

    private String password;
}
