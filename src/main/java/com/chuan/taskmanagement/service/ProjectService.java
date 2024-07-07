package com.chuan.taskmanagement.service;

import com.chuan.taskmanagement.dto.PageRequest;
import com.chuan.taskmanagement.dto.PageResponse;
import com.chuan.taskmanagement.dto.project.CreateProjectRequest;
import com.chuan.taskmanagement.dto.project.ProjectResponse;
import com.chuan.taskmanagement.dto.project.ProjectTuples;
import com.chuan.taskmanagement.dto.project.UpdateProjectRequest;

public interface ProjectService {

    void createProject(CreateProjectRequest projectRequest);

    void updateProject(UpdateProjectRequest projectRequest);

    void deleteProject(Long id);

    PageResponse<ProjectTuples> listProject(PageRequest pageRequest);

    ProjectResponse readProject(Long id);

}
