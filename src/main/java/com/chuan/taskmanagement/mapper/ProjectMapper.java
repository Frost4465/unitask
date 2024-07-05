package com.chuan.taskmanagement.mapper;

import com.chuan.taskmanagement.dto.project.ProjectResponse;
import com.chuan.taskmanagement.entity.Project;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProjectMapper {

    public ProjectResponse toResponse(Project project) {
        if (Objects.isNull(project)) {
            return null;
        }

        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setName(project.getName());
        projectResponse.setId(project.getId());
        return projectResponse;
    }
}
