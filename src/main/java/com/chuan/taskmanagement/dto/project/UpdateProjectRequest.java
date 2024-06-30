package com.chuan.taskmanagement.dto.project;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class UpdateProjectRequest {

    @NonNull
    private Long id;
    @Email
    private List<String> participants;

}
