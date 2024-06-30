package com.chuan.taskmanagement.dto.project;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class CreateProjectRequest {

    @NonNull
    private String name;
    @NonNull
    private String description;
    @Email
    private List<String> participants;

}
