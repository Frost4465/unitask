package com.chuan.taskmanagement.service;

import com.chuan.taskmanagement.dto.project.CreateProjectRequest;
import com.chuan.taskmanagement.dto.project.UpdateProjectRequest;

public interface ProjectService {

    void createProject(CreateProjectRequest projectRequest);
    void updateProject(UpdateProjectRequest projectRequest);
    void deleteProject(Long id);

}
