package com.chuan.taskmanagement.service;

import com.chuan.taskmanagement.dto.PageRequest;
import com.chuan.taskmanagement.dto.PageResponse;
import com.chuan.taskmanagement.dto.project.ProjectRequest;
import com.chuan.taskmanagement.dto.project.ProjectResponse;
import com.chuan.taskmanagement.dto.project.ProjectTuple;

public interface ProjectService {

    void createProject(ProjectRequest projectRequest);

    void updateProject(Long id, ProjectRequest projectRequest);

    void deleteProject(Long id);

    PageResponse<ProjectTuple> listProject(PageRequest pageRequest);

    ProjectResponse readProject(Long id);

}
