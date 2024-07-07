package com.chuan.taskmanagement.dto.project;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.util.Collection;

@Data
public class ProjectRequest {

    private Long id;
    @NonNull
    private String name;
    private String description;
    @NotBlank
    private String code;
    private Long leadId;
    private Collection<Long> participantIds;
}
