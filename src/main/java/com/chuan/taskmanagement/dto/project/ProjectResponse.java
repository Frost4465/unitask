package com.chuan.taskmanagement.dto.project;

import com.chuan.taskmanagement.dto.DropdownResponse;
import lombok.Data;

import java.util.List;

@Data
public class ProjectResponse {

    private Long id;
    private String name;
    private String code;
    private String description;
    private DropdownResponse leader;
    private List<DropdownResponse> projectMembers;
}
