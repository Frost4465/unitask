package com.chuan.taskmanagement.dto.user;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String oldPassword;
    private String newPassword;
}
