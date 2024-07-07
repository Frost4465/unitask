package com.chuan.taskmanagement.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileRequest {

    @NotBlank
    private String name;
}
