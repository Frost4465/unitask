package com.chuan.taskmanagement.dto.project;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Collection;

@Data
@Valid
public class ProjectRequest {

    @NotNull
    private String name;
    private String description;
    @NotBlank
    private String code;
    private Long leadId;
    private Collection<Long> projectMemberIds;
}
