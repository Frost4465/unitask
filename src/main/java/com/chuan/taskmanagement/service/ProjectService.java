package com.chuan.taskmanagement.service;

import com.chuan.taskmanagement.dto.PageRequest;
import com.chuan.taskmanagement.dto.project.ProjectRequest;
import com.chuan.taskmanagement.dto.project.ProjectResponse;
import com.chuan.taskmanagement.dto.project.ProjectTuple;
import org.springframework.data.web.PagedModel;

public interface ProjectService {

    void createProject(ProjectRequest projectRequest);

    void updateProject(Long id, ProjectRequest projectRequest);

    void deleteProject(Long id);

    PagedModel<ProjectTuple> listProject(PageRequest pageRequest);

    ProjectResponse readProject(Long id);

}
